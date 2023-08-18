<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<h1>게시글 수정</h1>
	<form method="post" action="./modifyBoard">
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
				<th>board_no</th>
				<td>
					<input type="text" name="boardNo" value="${boardNo}" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>member_id</th>
				<td>
					<input type="text" name="memberId" value="${memberId}" readonly="readonly">
				</td>
			</tr>	
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>