
window.onload = function () {

	let duration = getCountTimer();

	let timer = $('#count-down-timer');
	let min = parseInt(duration / 60);
	let sec = parseInt(duration % 60);

	timer.text(`${paddedFormat(min)}:${paddedFormat(sec)}`);
    startCountDown(--duration, timer);
}

function getCountTimer() {
	let quizTotalCnt = Number($('#quizTotalCnt').val());
	let quizPerTime = 60; // 60초
    let duration = quizTotalCnt * quizPerTime;
	return duration;
}

function startCountDown(duration, timer) {

    let secondsRemaining = duration;
    let min = 0;
    let sec = 0;

    let countInterval = setInterval(function () {

        min = parseInt(secondsRemaining / 60);
        sec = parseInt(secondsRemaining % 60);

		timer.text(`${paddedFormat(min)}:${paddedFormat(sec)}`);
		let progress = Math.floor(secondsRemaining/duration * 100);
		$('#progress-bar').text(progress + '%');
		$("#progress-bar").css("width", progress + '%');

        secondsRemaining = secondsRemaining - 1;

		if (secondsRemaining < 0) {
			clearInterval(countInterval);
			$('#count-down-timer').css('color', 'red');
		};

    }, 1000);
}

function paddedFormat(num) {
    return num < 10 ? "0" + num : num;
}

function trigger(obj, answer) {
	$(obj).toggleClass('active');
	$(obj).siblings().removeClass('active');
	$('#userAnswer').val(answer);
}

function startQuiz(param) {

	let form = $('#' + param);
	form.attr('action', '/quizMain.do');
	form.attr('target', '');
	let userId = prompt("이메일을 입력해주세요.");
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
	let form = $('#' + param);
	if (confirm("홈으로 가면, 모든 기록이 지워집니다. 이동하시겠습니까?")) {
		form.attr('action', '/index.do');
		form.attr('target', '');
		form.submit();
	}
}

function goStatistics(param) {
	let form = $('#' + param);
	form.attr('action', '/statistics.do');
	form.attr('target', '');
	form.submit();
}

function goQuiz(index) {

	let nextSrtNo = Number($('#srtNo').val()) + Number(index);
	let total = $('#quizTotalCnt').val();

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
	let url = '/quizAnsSave.do';
	let param = $('#quizForm').serialize();
	$.post(url, param, function (result) {
		if (result) {
			moveQuiz(index);
		} else {
			alert("오류가 발생하였습니다.");
		}
	});
}

function moveQuiz(index) {

	let form = $('#quizForm');

	if (!index) { // 결과 페이지

		let duration = Number(getCountTimer());
		let secondsRemaining = $('#count-down-timer').text().split(':');
		let timeSolving = duration - (Number(secondsRemaining[0]) * 60 + Number(secondsRemaining[1]));

		$('#minSolving').val(parseInt(timeSolving / 60));
		$('#secSolving').val(parseInt(timeSolving % 60));

		form.attr('action', '/quizResult.do');
		form.attr('target', '');
		form.submit();

	} else { // 다음 문제

		let url = '/quizAjax.do';
		let srtNo = Number($('#srtNo').val()) + Number(index);
		$('#srtNo').val(srtNo);

		$.post(url, form.serialize(), function (result) {
			$('i.active').removeClass('active');
			$('#quizDiv').replaceWith(result);
		});
	}
}
