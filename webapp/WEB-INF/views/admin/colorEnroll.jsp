<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/admin/colorEnroll.css">
 
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>
 
   <%@include file="../includes/admin/header.jsp" %>
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>색상 등록</span></div>
                    <div class="admin_content_main">
                    	<form action="/admin/colorEnroll.do" method="post" id="enrollForm">
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>색상 이름</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="colorName">
                    				<span id="warn_colorName">색상을 입력 해주세요.</span>
                    			</div>
                    		</div>
                   		</form>
                   			<div class="btn_section">
                   				<button id="cancelBtn" class="btn">취 소</button>
	                    		<button id="enrollBtn" class="btn enroll_btn">등 록</button>
	                    	</div> 
                    </div>
                </div>
                <%@include file="../includes/admin/footer.jsp" %>
 <script>
 
/* 등록 버튼 */
$("#enrollBtn").click(function(){   
	/* 검사 통과 유무 변수 */
    let nameCheck = false; 
    /* 입력값 변수 */
    let colorName = $('input[name=colorName]').val();        // 색상 이름
    /* 공란 경고 span태그 */
    let wColorName = $('#warn_colorName');   
    
    /* 작기 이름 공란 체크 */
    if(colorName ===''){
    	wColorName.css('display', 'block');
        nameCheck = false;
    } else{
    	wColorName.css('display', 'none');
        nameCheck = true;
    }
    if(nameCheck){
        $("#enrollForm").submit();    
    } else{
        return;
    }
});
 
/* 취소 버튼 */
$("#cancelBtn").click(function(){
    location.href="/admin/colorManage"
});
 
</script>

</body>
</html>
