/**
 * 更换验证码图片的方法
 * @author dengxiao
 */
function changeYzm(){
	var image = document.getElementById("yzm_image");
	image.src = "/user/reg/code?date="+Math.random();
}

/**
 * 网页加载成功之后立即显示一张验证码
 * @author dengxiao
 */
window.onload=function(){
	changeYzm();
}


