/**
 * 登录所需脚本
 */
;(function(){
 'use strict';
 var $login;
 var $register;
var $codebutton=$(".code_button");
$codebutton.on('click',sendcode);
$(".phone_number").blur(judge_phonenum);
$(".register_button>input").on('click',register_button);
$(".register_username>input").blur(username_out);
$(".register_password>input").blur(password_out);
$(".register_firstpw>input").blur(judge_password);
$(".register_message").on('click',judge_checked);
judge_lr();
$(".login_buttondiv>input").on("click",judge_login);
console.log("2f",$(".redirectURL").html());
})();
/* 登录函数*/
function login(){
$login=$('.login');
$register=$('.register');
$login.css("display","block");
$register.css("display","none");
$(".logo_login>h3").html("欢迎登录");
};
/* 注册函数*/
function register(){
	$login=$('.login');
	$register=$('.register');
	$login.css("display","none");
	$register.css("display","block");
	$(".logo_login>h3").html("欢迎注册");
};
//判断用户是否有注册需求
function judge_lr(){
	
	var $from=$(".from").html();
	console.log('1',$from);
	if($from=="true"){
		register();
		$(".logo_login>h3").html("欢迎注册");
	}
}
/*发送验证码动画*/
function sendcode(){
	 var $phone_number=$(".phone_number").val();
	  $.ajax({  
         type: "POST",  
         url: "http://localhost:8080/tsj/sendcode",  
         data: "phone_number="+$phone_number, 
         success: function(data){
        	 if(data.phonenum_exist!=null){
        		 
        		 $(".phone_div>p").html(data.phonenum_exist).show();
        	 }
        	 else{
        		 var times=60;
        		 var timer=null;
        		 timer=setInterval(function(){
        	        times--;
        		        if(times<=0){
        		        	$(".code_button").val('发送验证码');
        		            clearInterval(timer); 
        		            times=60;
        		            $(".code_button").css("disabled",false);
        		        }
        		        else{
        		        	$(".code_button").val(""+times+'秒后重试');
        		        	$(".code_button").css("disabled",true);
        		        }
        		    },1000); 
        	 }
        	      
            }  
      });  
}
//用户名移出函数
function username_out(){
	var $username=$(".register_username>input").val();
	if(!(/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4E00-\u9FA5]{3,14}$/.test($username))){
		$(".register_username>p").html("用户名以英文和汉字数字的组合,长度4-15位");
		$(".register_username>p").show();
	}
	else{
		$(".register_username>p").hide();
	}
}
//密码格式检验
function judge_password(){
	var $register_firstpw=$(".register_firstpw>input").val();
	if(!(/^[0-9a-zA-Z_#]{6,16}$/.test($register_firstpw))){
		$(".register_firstpw>p").html("密码以数字,字母,_,#组合且长度6-16位");
		$(".register_firstpw>p").show();
	}
	else{
		$(".register_firstpw>p").hide();
	}
}
//确认密码移出函数
function password_out(){
	var $password=$(".register_password>input").val();
	var $register_firstpw=$(".register_firstpw>input").val();
	if(!($password==$register_firstpw)){
		$(".register_password>p").html("两次密码不一致");
		$(".register_password>p").show();
	}
	else{
		$(".register_password>p").hide();
	}
}
//手机号格式验证
function judge_phonenum(){
	var $phone_num=$(".phone_number").val();
	if(!(/^1[34578]\d{9}$/.test($phone_num))){
		$(".phone_div>p").show();
	}
	else{
		$(".phone_div>p").hide();
	}
}
//判断是否选中协议
function judge_checked(){
	if($(".register_message").is(':checked')){
		$(".register_checkbox>span").hide();
	}
	else{
		$(".register_checkbox>span").html("请同意协议！").show();
	}
}
//注册
function register_button(){
	var $phone_num=$(".phone_number").val();
	var $password=$(".register_password>input").val();
	var $username=$(".register_username>input").val();
	var $register_firstpw=$(".register_firstpw>input").val();
	var $code=$(".code>input").val();
	if(!(/^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4E00-\u9FA5]{3,15}$/.test($username))){
		$(".register_username>p").html("用户名以英文和汉字数字的组合,长度4-15位");
		$(".register_username>p").show();
	}
	else{
		if(!(/^[0-9a-zA-Z_#]{6,16}$/.test($register_firstpw))){
			$(".register_firstpw>p").html("密码以数字,字母,_,#组合且长度6-16位");
			$(".register_firstpw>p").show();
		}
		else{
			if(!($password==$register_firstpw)){
				$(".register_password>p").html("两次密码不一致");
				$(".register_password>p").show();
			}
			else{
				if(!(/^1[34578]\d{9}$/.test($phone_num))){
					$(".phone_div>p").show();
				}
				else{
					if(!($(".register_message").is(':checked'))){
						$(".register_checkbox>span").html("请同意协议！").show();
					}
					else{
						$.ajax({  
					         type: "POST",  
					         url: "http://localhost:8080/tsj/register",  
					         data: "phone_number="+$phone_num+"&password="+$password+"&username="+$username+"&code="+$code, 
					         dataType : 'json',
					         success: function(data){ 
					         if(data.username_exist!=null){
					        	$(".register_username>p").html(data.username_exist).show(); 
					        	 }
					         if(data.code_error!=null){
					            $(".code>p").html(data.code_error).show();
					         }
					         if(data.register_success!=null){
					           $(".register_button>input").val(data.register_success);
					           $(".register_button>input").attr("disabled","disabled");
					           /*console.log('1',$(".redirectURL").html());*/
					           window.location.href=$(".redirectURL").html();
					         }
					         if(data.phonenum_exist!=null){
				        		 $(".phone_div>p").html(data.phonenum_exist).show();
				        	 }
					            }  
					      });
					}
					
				}
			}
		}
	}
	
	 
	
}
function judge_login(){
	var $username=$(".username_div>input").val();
	var $password=$(".password_div>input").val();
	$.ajax({  
        type: "POST",  
        url: "http://lx.com:80/tsj/judge_login",  
        data: "password="+$password+"&username="+$username,
        dataType : 'json',
        success: function(data){ 
        if(data.login_error!=null){
       	$(".loginerror_message").html(data.login_error).show();
       	 }
        else{
        	if($(".redirectURL").html()=="null"){
        		window.location.href="http://lx.com:80/tsj/";
        		
        	}
        	
        	else{
        		window.location.href=$(".redirectURL").html();
        		}
        	
        	
        }
        }
	});
}