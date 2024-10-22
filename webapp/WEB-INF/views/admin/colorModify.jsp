<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/admin/colorModify.css">

<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>
				<%@include file="../includes/admin/header.jsp" %>
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>색상 상세</span></div>
                    <div class="admin_content_main">
                    	<form id="modifyForm" action="/admin/colorModify" method="post">
	                   		<div class="form_section">
	                   			<div class="form_section_title">
	                   				<label>색상 번호</label>
	                   			</div>
	                   			<div class="form_section_content">
	                   				<input class="input_block" name="colorId" readonly="readonly" value="<c:out value='${colorInfo.colorId }'></c:out>">
	                   			</div>
	                   		</div>                    
	                   		<div class="form_section">
	                   			<div class="form_section_title">
	                   				<label>색상 이름</label>
	                   			</div>
	                   			<div class="form_section_content">
	                   				<input name="colorName" value="<c:out value='${colorInfo.colorName }'></c:out>" >
	                   				<span id="warn_colorName">색상 이름을 입력 해주세요.</span>
	                   			</div>
	                   		</div>
	                 		<div class="btn_section">
	                   			<button id="cancelBtn" class="btn">취소</button>
		                    	<button id="modifyBtn" class="btn modify_btn">수 정</button>
		                    	<button id="deleteBtn" class="btn delete_btn">삭 제</button>
		                    </div> 
	                    </form>
                    </div>                    
                </div>
                
                <form id="moveForm" method="get">
                	<input type="hidden" name="colorId" value='<c:out value="${colorInfo.colorId }"/>'>
                	<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
                	<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>' >
                	<input type="hidden" name="keyword" value='<c:out value="${cri.keyword }"/>'>
                </form>
				<%@include file="../includes/admin/footer.jsp" %>

<script>
let moveForm = $("#moveForm");
let modifyForm = $("#modifyForm");




/* 색상 상세 페이지 이동 버튼 */
$("#cancelBtn").on("click", function(e){
	
	e.preventDefault();
			
	moveForm.attr("action", "/admin/colorDetail")
	moveForm.submit();
	
});
/* 삭제 버튼 */
$("#deleteBtn").on("click", function(e){
	e.preventDefault();
	moveForm.find("input").remove();
	moveForm.append('<input type="hidden" name="colorId" value="${colorInfo.colorId}">');
	moveForm.attr("action", "/admin/colorDelete");
	moveForm.attr("method", "post");
	moveForm.submit();
});

/* 색상 수정 버튼 작동 및 유효성 검사 */
$("#modifyBtn").on("click", function(e){

	let colorName = $(".form_section_content input[name='colorName']").val();

	let	nameCk = false;
	
	e.preventDefault();
	
	if(!colorName){
		$("#warn_colorName").css("display", "block");
	} else {
		$("#warn_colorName").css("display", "none");
		nameCk = true;
	}

	
	if(nameCk){
		modifyForm.submit();	
	} else {
		return false;
	}
	
	
});
</script>
</body>
</html>