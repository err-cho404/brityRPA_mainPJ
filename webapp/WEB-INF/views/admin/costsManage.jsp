<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/admin/costsManage.css">
 
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>
<%@include file="../includes/admin/header.jsp" %>
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>지출 목록</span></div>
                    <div class="excel_export_wrap">
    <form action="/admin/costsExportToExcel" method="get">
        <button type="submit" class="btn excel_export_btn">엑셀로 내보내기</button>
    </form>
</div>
                     <div class="product_table_wrap">
                    	<!-- 상품 리스트 O -->
	                    <c:if test="${listcheck != 'empty'}">
	                    	<table class="product_table">
	                    		<thead>
	                    			<tr>
										<td class="th_column_1">지출 번호</td>
	                    				<td class="th_column_2">지출 내역</td>
	                    				<td class="th_column_3">지출 가격</td>
	                    				<td class="th_column_4">지출 방식</td>
	                    				<td class="th_column_5">지출 날짜</td>
	                    			</tr>
	                    		</thead>	
	                    		<c:forEach items="${list}" var="item">
                        <tr>
                            <td><c:out value="${item.costId}"/></td>
                            <td>
                                <a class="move" href="<c:out value='${item.costId}'/>">
                                    <c:out value="${item.costType}"/>
                                </a>    
                            </td>
                            <td><fmt:formatNumber value="${item.price}" type="currency"/></td>
                            <td><c:out value="${item.paymentMethod}"/></td>
                            <!-- 날짜 문자열을 자바스크립트로 포맷팅 -->
                            <td><span class="formattedDate"><c:out value="${item.costDate}"/></span></td>
                        </tr>
                    </c:forEach>

	                    	</table>
	                    </c:if>
	                    <!-- 상품 리스트 X -->
                		<c:if test="${listCheck == 'empty'}">
                			<div class="table_empty">
                				등록된 지출 내역이 없습니다.
                			</div>
                		</c:if> 
                	</div>
                	
                	<!-- 검색 영역 -->
                	<div class="search_wrap">
                		<form id="searchForm" action="/admin/costsManage" method="get">
                			<div class="search_input">
                    			<input type="text" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"></c:out>'>
                    			<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }"></c:out>'>
                    			<input type="hidden" name="amount" value='${pageMaker.cri.amount}'>
                    			<input type="hidden" name="type" value="G">
                    			<button class='btn search_btn'>검 색</button>                				
                			</div>
                		</form>
                	</div>
                	
                	<!-- 페이지 이름 인터페이스 영역 -->
                	<div class="pageMaker_wrap">
                		<ul class="pageMaker">
                			
                			<!-- 이전 버튼 -->
	                    	<c:if test="${pageMaker.prev}">
	                    		<li class="pageMaker_btn prev">
	                    			<a href="${pageMaker.pageStart - 1}">이전</a>
	                    		</li>
	                    	</c:if>
	                    	
	                    	<!-- 페이지 번호 -->
	                    	<c:forEach begin="${pageMaker.pageStart}" end="${pageMaker.pageEnd}" var="num">
	                    		<li class="pageMaker_btn ${pageMaker.cri.pageNum == num ? "active":""}">
	                    			<a href="${num}">${num}</a>
	                    		</li>
	                    	</c:forEach>
	                    	
	                    	<!-- 다음 버튼 -->
	                    	<c:if test="${pageMaker.next}">
	                    		<li class="pageMaker_btn next">
	                    			<a href="${pageMaker.pageEnd + 1 }">다음</a>
	                    		</li>
	                    	</c:if>
	                    	
	                    </ul>
                	</div>
                	
                	<form id="moveForm" action="/admin/costsManage" method="get" >
 						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
						<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                	</form>
                </div>
    <%@include file="../includes/admin/footer.jsp" %>
<script>
$(document).ready(function(){
	
	$('.formattedDate').each(function() {
	    let dateText = $(this).text();
	    let date = new Date(dateText);
	    // 현재 날짜에서 하루를 더하여 출력
	    let utcDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate() ));
	    let formattedDate = utcDate.toISOString().split('T')[0];
	    $(this).text(formattedDate);
	});
	
	let eResult = '<c:out value="${enroll_result}"/>';
	
	checkResult(eResult);
	
	function checkResult(result){
		
		if(result === ''){
			return;
		}
		
		alert("지출'"+ eResult +"'을 등록하였습니다.");
		
	}

});
	let searchForm = $('#searchForm');
	let moveForm = $('#moveForm');

	/* 색상 검색 버튼 동작 */
	$("#searchForm button").on("click", function(e){
		
		e.preventDefault();
		
		/* 검색 키워드 유효성 검사 */
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하십시오");
			return false;
		}
		
		searchForm.find("input[name='pageNum']").val("1");
		
		searchForm.submit();
		
	});


	/* 페이지 이동 버튼 */
	$(".pageMaker_btn a").on("click", function(e){
		
		e.preventDefault();
		
		moveForm.find("input[name='pageNum']").val($(this).attr("href"));
		
		moveForm.submit();
		
	});
/* 상품 조회 페이지 */
$(".move").on("click", function(e){
	
	e.preventDefault();
	
	moveForm.append("<input type='hidden' name='costId' value='"+$(this).attr("href") + "'>");
	moveForm.attr("action", "/admin/costsDetail");
	moveForm.submit();
	
	
});
/* 수정 성공 이벤트 */
let modify_result = '${modify_result}';

if(modify_result == 1){
	alert("수정 완료");
}	
/* 삭제 결과 경고창 */
let delete_result = '${delete_result}';

if(delete_result == 1){
	alert("삭제 완료");
}
</script>
</body>
</html>