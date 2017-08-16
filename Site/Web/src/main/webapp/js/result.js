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
    $(".result_shopclass>h3").on('mouseover',function(){
        $(".result_show").show();
    });
    $(".result_shopclass>h3").on('mouseout',function(){
        $(".result_show").hide();
    });
    $(".result_show").on('mouseover',function(){
        $(".result_show").show();
    });
    $(".result_show").mouseout(function(){
        $(".result_show").hide();
    });
 /*jquery实现的分类（导航栏）动画*/
 var $shopClass_show=$(".shopClass_show");
     $shopClass_show.each(function(index){
    	var $this=$(this);
    	/*console.log('index',index);*/
    	$this.mouseover(function(){
    		$this.attr("id",index);
    		$this.children('.shop_detail').css("display","block");
    		$this.css("background-color","#0066FF");
    		$this.find("span").css("color","#e6e6e6");
    		/*$this.find("span").removeClass();
    		$this.find("span").addClass("span"+$this.attr("id"));*/

    	});
    	$this.mouseout(function(){
    		$this.css("background-color","#e6e6e6");
    		$this.children('.shop_detail').css("display","none");
    		$this.find("span").css("color","#2c2c2c");
    		/*$this.find("span").removeClass();
    		$this.find("span").addClass("oldspan"+$this.attr("id"));*/
    	});

     });

     var menu=document.getElementById('menu');
  	var ps=menu.getElementsByTagName('p');
  	var uls=menu.getElementsByTagName('ul');
  	for(var i=0;i<ps.length;i++)
  	    {   ps[i].id=i;
  	        ps[i].onclick=function(){
  	        var u=uls[this.id];
  	     	if(u.style.display=='block'){
  	     	u.style.display='none';}
 	     	else{u.style.display='block';}
  	     }
  	 }
     
})();