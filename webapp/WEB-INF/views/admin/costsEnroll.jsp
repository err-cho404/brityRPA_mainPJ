<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/admin/costsEnroll.css?after">
<link rel="stylesheet" href="//code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
 <script src="https://cdn.ckeditor.com/ckeditor5/26.0.0/classic/ckeditor.js"></script>
 <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
</head>
<body>
 <%@include file="../includes/admin/header.jsp" %>
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>지출 등록</span></div>
                    <div class="admin_content_main">
                    <form action="/admin/costsEnroll" method="post" id="enrollForm">
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 내역</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="costType">
                    				<span class="ck_warn costType_warn">지출 내역을 입력해주세요.</span>
                    			</div>
                    		</div>          
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 일자</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="costDate" autocomplete="off" readonly="readonly">
                    			<span class="ck_warn costDate_warn">지출 일자를 선택해주세요</span>
                    			</div>
                    		</div>            
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 방식</label>
                    			</div>
                    			<div class="form_section_content">
                    				<select id="paymentMethod" name="paymentMethod" size="1">
                    				<option value="현금">현금</option>
                    				<option value="카드">카드</option>
                    				<option value="송금">송금</option>
                    				<option value="기타">기타</option>
									</select> <br> <br>
                    			</div>
                    		</div>     
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 비용</label>
                    			</div>
                    			<div class="form_section_content">
                    				<input name="price" value="0">
                    				<span class="ck_warn price_warn">지출 비용을 입력해주세요.</span>
                    			</div>
                    		</div>        		
                    		<div class="form_section">
                    			<div class="form_section_title">
                    				<label>지출 설명</label>
                    			</div>
                    			<div class="form_section_content bit">
                    				<textarea name="description" id="description_textarea"></textarea>
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

	let enrollForm = $("#enrollForm")
	
/* 취소 버튼 */
$("#cancelBtn").click(function(){
	
	location.href="/admin/costsManage"
	
});

/* 지출 등록 버튼 */
$("#enrollBtn").on("click",function(e){
	
	e.preventDefault();
	/* 체크 변수 */
	let costTypeCK = false;
	let costDateCk =false;
	let paymentMethodCk =false;
	let priceCk =false;
	let productStockCk =false;
	let productDiscountCk =false;
	
	/* 체크 대상 변수 */
	let costType = $("input[name='costType']").val();
	let costDate = $("input[name='costDate']").val();
	let paymentMethod = $("select[name='paymentMethod']").val();
	let price = $("input[name='price']").val();
	if(costType){
		$(".costType_warn").css('display','none');
		costTypeCK = true;
	} else {
		$(".costType_warn").css('display','block');
		costTypeCK = false;
	}
	if(costDate){
		$(".costDate_warn").css('display','none');
		costDateCk = true;
	} else {
		$(".costDate_warn").css('display','block');
		costDateCk = false;
	}
	
	if(paymentMethod){
		$(".paymentMethod_warn").css('display','none');
		paymentMethodCk = true;
	} else {
		$(".paymentMethod_warn").css('display','block');
		paymentMethodCk = false;
	}	
	
	if(!isNaN(price)){
		$(".price_warn").css('display','none');
		priceCk = true;
	} else {
		$(".price_warn").css('display','block');
		priceCk = false;
	}
	if(costTypeCK && costDateCk && paymentMethodCk && priceCk){
		//alert('통과');
		enrollForm.submit();
	} else {
		return false;
	}
	
});
/* 위지윅 적용 */

/* 지출 설명 */
	ClassicEditor
		.create(document.querySelector('#description_textarea'))
		.catch(error=>{
			console.error(error);
		});
/* 설정 */
const config = {
	dateFormat: 'yy-mm-dd',
	showOn : "button",
	buttonText:"날짜 선택",
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    dayNames: ['일','월','화','수','목','금','토'],
    dayNamesShort: ['일','월','화','수','목','금','토'],
    dayNamesMin: ['일','월','화','수','목','금','토'],
    yearSuffix: '년',
    changeMonth: true,
    changeYear: true
}

/* 캘린더 */
$(function() {
  $( "input[name='costDate']" ).datepicker(config);
});
</script> 	
</body>
</html>