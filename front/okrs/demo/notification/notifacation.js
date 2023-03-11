// 判断浏览器是否支持唤醒
if (window.Notification) {
	debugger;
	let popNotice = () => {
		if (!Notification.permission === 'granted') return;
		const notification = new Notification('核客互动', {
			body: '提示信息'
		});
		// 点击通知的回调函数
		notification.onclick = function () {
			window.open('https://baidu.com');
			notification.close();
		};
	};
	/* 授权过通知 */
	if (Notification.permission === 'granted') {
		popNotice();
	} else {
		/* 未授权，先询问授权 */
		Notification.requestPermission(function (permission) {
			popNotice();
		});
	}
}
