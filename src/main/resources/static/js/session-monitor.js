/**
 * 会话监控 — 检测账号在其他地方登录导致当前会话失效
 *
 * 使用方式:
 *   1. 在页面中引入此JS文件
 *   2. 初始化: SessionMonitor.init({ onSessionExpired: function() { ... } });
 */
(function(window) {
    'use strict';

    var SessionMonitor = {
        config: {
            checkInterval: 30000,
            checkUrl: '/api/session/check',
            onSessionExpired: null,
            enabled: true
        },

        timer: null,
        isExpired: false,

        /**
         * 初始化会话监控
         */
        init: function(options) {
            if (options) {
                this.config = Object.assign(this.config, options);
            }

            if (this.config.enabled) {
                this.startMonitoring();
                this.setupAjaxInterceptor();
            }
        },

        /**
         * 开始监控
         */
        startMonitoring: function() {
            var self = this;

            if (this.timer) {
                clearInterval(this.timer);
            }

            this.timer = setInterval(function() {
                self.checkSession();
            }, this.config.checkInterval);
        },

        /**
         * 停止监控
         */
        stopMonitoring: function() {
            if (this.timer) {
                clearInterval(this.timer);
                this.timer = null;
            }
        },

        /**
         * 检测会话状态
         */
        checkSession: function() {
            if (this.isExpired) {
                return;
            }

            var self = this;
            fetch(this.config.checkUrl, {
                headers: { 'X-Requested-With': 'XMLHttpRequest' }
            })
            .then(function(response) {
                if (response.status === 401) {
                    return response.json().then(function(data) {
                        if (data.reason === 'session_expired_by_concurrent_login') {
                            self.handleSessionExpired(data);
                        }
                    });
                }
            })
            .catch(function() {});
        },

        /**
         * 设置 AJAX 拦截器 — 拦截业务请求中的 401 响应
         */
        setupAjaxInterceptor: function() {
            var self = this;

            if (window.fetch) {
                var originalFetch = window.fetch;
                window.fetch = function() {
                    return originalFetch.apply(this, arguments).then(function(response) {
                        if (response.status === 401 && !self.isExpired) {
                            return response.clone().json().then(function(data) {
                                if (data.reason === 'session_expired_by_concurrent_login') {
                                    self.handleSessionExpired(data);
                                }
                                return response;
                            }).catch(function() {
                                return response;
                            });
                        }
                        return response;
                    });
                };
            }
        },

        /**
         * 处理会话过期
         */
        handleSessionExpired: function(response) {
            if (this.isExpired) {
                return;
            }

            this.isExpired = true;
            this.stopMonitoring();

            this.showExpiredNotification(response);

            if (typeof this.config.onSessionExpired === 'function') {
                this.config.onSessionExpired(response);
            } else {
                setTimeout(function() {
                    window.location.href = '/logged-out';
                }, 3000);
            }
        },

        /**
         * 显示会话过期通知
         */
        showExpiredNotification: function(response) {
            var msg = (response && response.msg) ? response.msg : '会话已失效，即将跳转...';

            var notification = document.createElement('div');
            notification.style.cssText =
                'position: fixed; top: 20px; left: 50%; transform: translateX(-50%); ' +
                'background: #ff6b6b; color: white; padding: 15px 30px; ' +
                'border-radius: 5px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); ' +
                'z-index: 99999; font-size: 16px; font-weight: bold; ' +
                'animation: slideDown 0.3s ease-out;';

            notification.innerHTML =
                '<div style="text-align: center;">' +
                '<div style="margin-bottom: 5px;">会话已失效</div>' +
                '<div style="font-size: 14px; font-weight: normal;">' + msg + '</div>' +
                '</div>';

            var style = document.createElement('style');
            style.textContent =
                '@keyframes slideDown {' +
                '  from { transform: translateX(-50%) translateY(-100%); opacity: 0; }' +
                '  to { transform: translateX(-50%) translateY(0); opacity: 1; }' +
                '}';
            document.head.appendChild(style);

            document.body.appendChild(notification);
        }
    };

    window.SessionMonitor = SessionMonitor;

})(window);
