
$('.btn-grp i').click(function () {
	$(this).toggleClass('active');
	$(this).siblings().removeClass('active');
});

function startQuiz(param) {

	var form = $('#' + param);
	form.attr('action', '/quiz-main.do');
	form.attr('target', '');
	var userId = prompt("이메일을 입력해주세요.");
	if(userId) {
		$('#userId').val(userId);
		form.submit();
	} else {
		if(confirm("이메일을 입력하지 않는다면, 결과를 확인할 수  없습니다. \n그래도 계속하시겠습니까?")) {
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

function goPrev() {

	var prevSrtNo = Number($('#srtNo').val()) - 1;

	if (!prevSrtNo) {
		alert("첫 번째 문제입니다.");
		return;
	} else if (!$('.active').length) {
		if (confirm("답을 선택하지 않았습니다. 이전 문제로 가시겠습니까?")) {
			moveQuiz(prevSrtNo);
		}
	} else {
		moveQuiz(prevSrtNo);
	}
}

function goNext() {

	var nextSrtNo = Number($('#srtNo').val()) + 1;
	var total = $('#total').val();

	if (nextSrtNo > total) {
		if (confirm("마지막 문제입니다. \n결과로 이동하시겠습니까?")) {
			moveQuiz(nextSrtNo);
		}
	} else if (!$('.active').length) {
		if (confirm("답을 선택하지 않았습니다. 다음 문제로 가시겠습니까?")) {
			moveQuiz(nextSrtNo);
		}
	} else {
		moveQuiz(nextSrtNo);
	}
}

function moveQuiz(srtNo) {
	$('#srtNo').val(srtNo);
	var url = '/quiz-ajax.do';
	var param = $('#quizForm').serialize();
	$.post(url, param, function (data) {
		$('i.active').removeClass('active');
		$('#quizDiv').replaceWith(data);
	});
}

function trigger(answer) {
	$('#userAnswer').val(answer);
}