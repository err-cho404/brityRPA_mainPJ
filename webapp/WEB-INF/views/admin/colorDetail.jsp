<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/admin/colorDetail.css?after">

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
                   				<input class="input_block" name="colorName" readonly="readonly" value="<c:out value='${colorInfo.colorName }'></c:out>" >
                   			</div>
                   		</div>
                   			<div class="btn_section">
                   				<button id="cancelBtn" class="btn">색상 목록</button>
	                    		<button id="modifyBtn" class="btn modify_btn">수 정</button>
	                    	</div> 
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

/* 색상 관리 페이지 이동 버튼 */
$("#cancelBtn").on("click", function(e){
	
	e.preventDefault();
	
	$("input[name=colorId]").remove();
	moveForm.attr("action", "/admin/colorManage")
	moveForm.submit();
	
});

/* 색상 수정 페이지 이동 버튼 */
$("#modifyBtn").on("click", function(e){
	
	e.preventDefault();
	
	moveForm.attr("action", "/admin/colorModify");
	moveForm.submit();
	
});
</script>
</body>
</html>