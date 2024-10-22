<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/admin/costsDetail.css?after">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/26.0.0/classic/ckeditor.js"></script>
</head>
<body>
<%@include file="../includes/admin/header.jsp" %>
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>지출 상세</span></div>

                    <div class="admin_content_main">

                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 내역</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="costType" value="<c:out value="${costsInfo.costType}"/>" disabled>
                    			</div>
                    		</div>       
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 일자</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="costDate" autocomplete="off" readonly="readonly" value="<c:out value="${costsInfo.costDate}"/>" disabled>                    				
                    			</div>
                    		</div>
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 방식</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="paymentMethod" autocomplete="off" readonly="readonly" value="<c:out value="${costsInfo.paymentMethod}"/>" disabled>	
                    			</div>
                    		</div>
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 비용</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="price" value="<c:out value="${costsInfo.price}"/>" disabled>
                    			</div>
                    		</div>         		
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 설명</label>
                    			</div>
                    			<div class="form_section_content bit">
                    				<textarea name="description" id="description_textarea" disabled>${costsInfo.description}</textarea>
                    			</div>
                    		</div>
                   			<div class="btn_section">
                   				<button id="cancelBtn" class="btn">지출 목록</button>
	                    		<button id="modifyBtn" class="btn enroll_btn">수정 </button>
	                    	</div> 
                    </div>      

                	
                	<form id="moveForm" action="/admin/costsManage" method="get" >
 						<input type="hidden" name="pageNum" value="${cri.pageNum}">
						<input type="hidden" name="amount" value="${cri.amount}">
						<input type="hidden" name="keyword" value="${cri.keyword}">
                	</form>
                	
                </div>
 				<%@include file="../includes/admin/footer.jsp" %>
 				
<script>
$(document).ready(function(){
	/* 출시일 값 가공 */
	let costDate = '${costsInfo.costDate}';
	let length = costDate.indexOf(" ");
	
	costDate = costDate.substring(0, length);
	
	$("input[name='costDate']").attr("value", costDate);
	/* 지출 설명 */
	ClassicEditor
		.create(document.querySelector('#description_textarea'))
		.then(editor => {
			console.log(editor);
			editor.isReadOnly = true;
		})
		.catch(error=>{
			console.error(error);
		});
	
});
/* 목록 이동 버튼 */
$("#cancelBtn").on("click", function(e){
	e.preventDefault();
	$("#moveForm").submit();	
});	

/* 수정 페이지 이동 */
$("#modifyBtn").on("click", function(e){
	e.preventDefault();
	let addInput = '<input type="hidden" name="costId" value="${costsInfo.costId}">';
	$("#moveForm").append(addInput);
	$("#moveForm").attr("action", "/admin/costsModify");
	$("#moveForm").submit();
});	
	</script>
</body>
</html>