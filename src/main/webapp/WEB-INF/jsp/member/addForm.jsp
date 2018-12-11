<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>회원가입</title>
	<!-- meta tags 필요 -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

</head>
<body>
<div class="container">
	<h1>회원가입</h1>
	<br>
	<form action="add" method="post">
		<div class="form-group row">
			<label for="id" class="col-sm-2 col-form-label">아이디 :</label>
			<div class="col-sm-4">
				<input type="text" name="id" id="id" class="form-control" placeholder="아이디" required>
			</div>
			<div class="id col-sm-6"></div>
		</div>
		<div class="form-group row">
			<label for="passwd" class="col-sm-2 col-form-label">패스워드 :</label>
			<div class="col-sm-4">
				<input type="password" name="passwd" id="passwd" class="form-control" placeholder="패스워드" required>
			</div>
		</div>
		<div class="form-group row">
			<label for="name" class="col-sm-2 col-form-label">이름 :</label>
			<div class="col-sm-4">
				<input type="text" name="name" id="name" class="form-control" placeholder="이름" required>
			</div>
		</div>
		<div class="form-group row">
			<label for="age" class="col-sm-2 col-form-label">나이 :</label>
			<div class="col-sm-4">
				<input type="number" name="age" id="age" min="0" max="200" class="form-control" placeholder="나이">
			</div>
		</div>
		
		<fieldset class="form-group">
			<div class="row">
				<legend class="col-form-legend col-sm-2">성별 :</legend>
				<div class="col-sm-4">
					<div class="form-check form-check-inline">
						<label class="form-check-label">
							<input type="radio" name="gender" id="male" value="남" class="form-check-input">
							남성
						</label>
					</div>
					<div class="form-check form-check-inline">
						<label class="form-check-label">
							<input type="radio" name="gender" id="female" value="여" class="form-check-input">
							여성
						</label>
					</div>
				</div>
			</div>
		</fieldset>
		
		<div class="form-group row">
			<label for="email" class="col-sm-2 col-form-label">이메일 :</label>
			<div class="col-sm-4">
				<input type="email" name="email" id="email" class="form-control" placeholder="이메일" aria-describedby="emailHelp">
				<small id="emailHelp" class="form-text text-muted">이메일 주소는 제3자에게 제공되지 않고 안전하게 관리됩니다.</small>
			</div>
		</div>
		<br>
		<input type="submit" value="회원가입" class="btn btn-primary">
	</form>
</div>
	
	
	<!-- Optional JavaScript -->
    <!-- 먼저 jQuery가 오고 그 다음 Popper.js 그 다음 Bootstrap JS -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    
    <script>
    	$('#id').keyup(function () {
    		var id = $(this).val();
    		
    		$.ajax({
    			url: 'ajaxCheckDuplicateId',
    			data: {id: id},
    			success: function (result) {
    				$('div.id').html(result);
    			}
    		});
    	});
    </script>
</body>
</html>