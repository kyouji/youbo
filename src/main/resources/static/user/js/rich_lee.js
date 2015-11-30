window.onload = function(){
		$('.scroll_box').height($('.scroll_box').width()*0.8)
		$('.scroll_box img').width($(window).width())
		var len = $('.scroll_go img').size();

		$('.scroll_go').width($('.scroll_go img').width()*len)
		banner_scroll();
		$('.scro_btn ul').width($('.scro_btn ul li').width()*len + 6*len +10)
		
	};


function banner_scroll(){
		
			var go = window.screen.width;
			var timer = null;
			var iNow = 0 ;    //记录 索引
			var iScroll = 0;  //滑动的距离      每次滑动的距离 相加  储存在myX里面
			var straX = 0;  //最开始的坐标位子
			var myX = 0;//用来储存滑动的距离
			//移除  冒泡 和 默认
			/*$(document).bind('touchmove',function(){
				return false;
			})*/
			//li的添加
			var len_img = $('.scroll_go img').size();
			for(var i=0;i<len_img;i++){
				$('.scro_btn ul').append('<li></li>')
			};
			$('.scro_btn ul li').eq(0).addClass('my_style');
			
			
			
			$('.scroll_go').bind('touchstart',function(){
				$('.scroll_go').css({WebkitTransition:'0'})
				straX = event.changedTouches[0].pageX;				
				myX	= iScroll;
			});
			
			$('.scroll_go').bind('touchmove',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					iScroll= myX + disX;
										
					//$('.box').css({WebkitTransform:'translateX('+iScroll+'px)'})
					$('.scroll_go').animate({left:''+iScroll+'px'},0)
			});
			
			$('.scroll_go').bind('touchend',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					//iScroll = myX + disX;
				iScroll= myX + disX;
				iNow = iScroll/go;
				iNow = Math.round(iNow)
				
				iScroll =iNow*go;
				var dis =$('.scroll_go').width()-$('.scroll_go img').width();
				if(iScroll>0){
					iScroll =0;
				}else if(-iScroll>dis){
					iScroll=-dis
				};
		
				
				$('.scroll_go').animate({left:''+iScroll+'px'},400)
				//console.log(-iNow)
				var num = -iNow;
				if(num<0){
					num=0
				}else if(num>len_img-1){
					num=len_img-1
				}
				$('.scro_btn ul li').removeClass('my_style');
				$('.scro_btn ul li').eq(num).addClass('my_style');
			});
		
			
			
			
			
			
			
			
			
			
			
			
			
			

	};