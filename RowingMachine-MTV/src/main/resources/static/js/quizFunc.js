
function trigger(obj, answer) {
	$(obj).toggleClass('active');
	$(obj).siblings().removeClass('active');
	$('#userAnswer').val(answer);
}

function startQuiz(param) {

	var form = $('#' + param);
	form.attr('action', '/quizMain.do');
	form.attr('target', '');
	var userId = prompt("이메일을 입력해주세요.");
	if (userId) {
		$('#userId').val(userId);
		form.submit();
	} else {
		if (confirm("이메일을 입력하지 않는다면, 결과를 확인할 수  없습니다. \n그래도 계속하시겠습니까?")) {
			form.submit();
		}
	}
}

function goHome(param) {
	var form = $('#' + param);
	if (confirm("홈으로 가면, 모든 기록이 지워집니다. 이동하시겠습니까?")) {
		form.attr('action', '/index.do');
		form.attr('target', '');
		form.submit();
	}
}

function goStatistics(param) {
	var form = $('#' + param);
	form.attr('action', '/statistics.do');
	form.attr('target', '');
	form.submit();
}

function goQuiz(index) {

	var nextSrtNo = Number($('#srtNo').val()) + Number(index);
	var total = $('#quizTotalCnt').val();

	if (!nextSrtNo) {
		alert("첫 번째 문제입니다.");
		return;
	} else if (total < nextSrtNo) {
		if (confirm("마지막 문제입니다. \n결과로 이동하시겠습니까?")) {
			quizAnsSave();
		}
	} else {
		if (!$('.active').length) {
			if (confirm("답을 선택하지 않았습니다. 다음 문제로 가시겠습니까?")) {
				quizAnsSave(index);
			}
		} else {
			quizAnsSave(index);
		}
	}
}

function quizAnsSave(index) {
	var url = 'quizAnsSave.do';
	var param = $('#quizForm').serialize();
	$.post(url, param, function (result) {
		if (result) {
			moveQuiz(index);
		} else {
			alert("오류가 발생하였습니다.");
		}
	});
}

function moveQuiz(index) {

	var form = $('#quizForm');

	if (!index) { // 결과 페이지

		form.attr('action', '/quizResult.do');
		form.attr('target', '');
		form.submit();

	} else { // 다음 문제

		var url = '/quizAjax.do';
		var srtNo = Number($('#srtNo').val()) + Number(index);
		$('#srtNo').val(srtNo);

		$.post(url, form.serialize(), function (result) {
			$('i.active').removeClass('active');
			$('#quizDiv').replaceWith(result);
		});
	}
}
