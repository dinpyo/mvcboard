<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 추가</title>
</head>
<body>
	<h1>게시글 추가</h1>
	<form method="post" action="/board/addBoard" enctype="multipart/form-data">
		<table>
			<tr>
				<th>local_name</th>
				<td>
					<input type="text" name="localName" required="required">
				</td>
			</tr>
			<tr>
				<th>board_title</th>
				<td>
					<input type="text" name="boardTitle" required="required">
				</td>
			</tr>
			<tr>
				<th>board_content</th>
				<td>
					<textarea rows="3" cols="50" name="boardContent" required="required"></textarea>
				</td>
			</tr>
			<tr>
				<th>member_id</th>
				<td>
					<input type="text" name="memberId" required="required">
				</td>
			</tr>		
			<tr>
				<th>파일 업로드</th>
				<td>
					<input type="file" name="multipartFile" multiple="multiple">
				</td>
			</tr>		
		</table>
		<button type="submit">추가</button>
	</form>
</body>
</html>