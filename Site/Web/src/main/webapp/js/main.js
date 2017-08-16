/**
 * 
 */
;(function(){
 'use strict';
 var $mycenter=$(".mycenter");
 var $customer=$(".customer_service");
 /*最顶部我的中心和客服服务动画*/
 $mycenter.mouseover(function(){
	 
	 $(".mycenter_detail").css("display","block");
 });
 $mycenter.mouseout(function(){
	 $(".mycenter_detail").css("display","none");
 });
 $customer.mouseover(function(){
	 $(".service_detail").css("display","block");
 });
 $customer.mouseout(function(){
	 $(".service_detail").css("display","none");
 });
 /*jquery实现的分类（导航栏）动画*/
 var $shopClass_show=$(".shopClass_show");
     $shopClass_show.each(function(index){
    	var $this=$(this);
    	/*console.log('index',index);*/
    	$this.mouseover(function(){

    		$this.children('.shop_detail').css("display","block");
    		$this.css("background-color","#0066FF");
    		$this.find("span").css("color","#e6e6e6");
    		/*$this.find("span").removeClass();*/
    		/*$this.find("span").addClass("newspan");*/
    		
    	});
    	$this.mouseout(function(){
    		$this.css("background-color","#e6e6e6");
    		$this.children('.shop_detail').css("display","none");
    		$this.find("span").css("color","#2c2c2c");
    		/*$this.find("span").removeClass();
    		$this.find("span").addClass("oldspan");*/
    	});
    	
     });
/*js实现的分类（导航栏）动画*/
 /*var shopClass_show=document.getElementsByClassName('shopClass_show');*/
 /*for(var i=0;i<shopClass_show.length;i++){
	 shopClass_show[i].onmouseover=function(){
	    var shop_detail=this.getElementsByClassName("shop_detail");
	    shop_detail[0].style.display="block";
	 };	 
 shopClass_show[i].onmouseout=function(){
	 var shop_detail=this.getElementsByClassName("shop_detail");
	    shop_detail[0].style.display="none";
	 }
 }*/
/* 幻灯片动画*/
     addSliders();
     var banner_auto;
     banner_auto=setInterval(function(){
    	 point(1);
     },3000);
     switchSlidder(1);
     var $leftpoint=$(".leftpoint");
     var $rightpoint=$(".rightpoint");
     $leftpoint.on('click',function(){
    	 clearInterval(banner_auto);
    	 point(-1);
    	 banner_auto=setInterval(function(){
    	   	 point(1);
    	    },3000);
    	 });
     $rightpoint.on('click',function(){
    	 clearInterval(banner_auto);
    	 point(1);
    	 banner_auto=setInterval(function(){
    	   	 point(1);
    	    },3000);
    });
     //
     $(".ctrl_i").each(function(){
    	 $(this).on('click',function(){
    		 clearInterval(banner_auto);//清除当前的定时器
    		 switchSlidder(parseInt($(this).attr("id").replace(/ctrl_/,"")));
    		 banner_auto=setInterval(function(){
        	   	 point(1);
        	    },3000);
    	 }) 
     });
    	 
     
     
     
})();
//3添加幻灯片的操作（所有幻灯片和对应的按钮）
/*假设获取到后端json数据*/
function addSliders () { 
	 
	var data=[
              {img:1},
              {img:2},
              {img:3},
              {img:4},
              {img:5}
              
      ];
    //3.1获取模板(去除空格)
    var tpl_banner=$("#banner_img").html().replace(/^\s*/,'').replace(/\s*$/,'');
    var tpl_ctrl=$("#ctrl").html().replace(/^\s*/,'').replace(/\s*$/,'');
    //3.2定义最终输出的html变量
    var out_main=[];
    var out_ctrl=[];
    //3.3遍历所有数据
    for (i=0;i<data.length;i++){
        var _html_main=tpl_banner.replace(/{{index}}/g,data[i].img);
        var _html_ctrl=tpl_ctrl.replace(/{{index}}/g,data[i].img);
        out_main.push(_html_main); /*push将字符串添加到数组中去*/
        out_ctrl.push(_html_ctrl);
        }
        //3.4把html回写到对应DOM里边
        $("#banner_img").html(out_main.join(''));/*join用于把数组中的所有元素放入一个字符串中''指定要使用的分隔符*/
        $("#ctrl").html(out_ctrl.join(''));
        //增加 #main_background
        var newbanner=$("#banner_img").html();
        newbanner+=tpl_banner;
        $("#banner_img").html(newbanner);
        
        var test=document.getElementById("banner_{{index}}");
        var $main_background=$(test);//将js对象转换为jquery对象
        $main_background.attr("id","main_background");
    
    
}
/*切换到当前的页面*/
function  switchSlidder(n) {
	
    var $banner=$("#banner_"+n);
    var $ctrl=$("#ctrl_"+n);
    //获取所有的幻灯片、所有的控制按钮
    var clear_banner=$(".banner_i_active");//获得所有当前展示的幻灯片
    var clear_ctrl=$(".ctrl_i_active");//获得所有当前展示的幻灯片按钮
    //清除他们的active样式
    clear_banner.each(function(index){
    	
    	$(this).removeClass("banner_i_active");
    	 
    });
    clear_ctrl.each(function(index){
    	
    	 $(this).removeClass("ctrl_i_active");
    });
   
    
    //显示当前幻灯片
    $banner.addClass("banner_i_active");
    $ctrl.addClass("ctrl_i_active");
    
    //给当前幻灯片添加背景 
   setTimeout(function () {
        $("#main_background").html($banner.html());/*延迟1s将上张背景图换成本张*/
    },1000);
}
/*幻灯片左右切换按钮执行函数*/
function point(index){
	 var banner_current_id=$(".banner_i_active").attr("id").replace(/banner_/,"");//获得当前幻灯片的位置
	 var banner_next_id=parseInt(banner_current_id)+index;//位置加一
	 /*console.log(banner_next_id);
	 console.log($(".ctrl_i").length);*/
	 if(banner_next_id>$(".ctrl_i").length)
		 {
		 banner_next_id=1;
		 switchSlidder(banner_next_id);
		 }
	 else if(banner_next_id<1){
		 banner_next_id=$(".ctrl_i").length;
		 switchSlidder(banner_next_id);
	 }
	 else{switchSlidder(banner_next_id);}
	 
}
