<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
	<h1>게시글 삭제</h1>
	<form method="post" action="./removeBoard">
		<table>
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
		<button type="submit">삭제</button>
	</form>
	
</body>
</html>