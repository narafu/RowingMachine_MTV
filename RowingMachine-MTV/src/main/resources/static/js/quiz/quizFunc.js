
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
		let progress = Math.floor(secondsRemaining / duration * 100);
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

function invitation() {
	let url = '/quiz/modal/invitation';
	let userNm = $('#userNm').val();
	$.ajax(url, { 'userNm': userNm }).done(function (modalHtml) {
		$('#modal').html(modalHtml);
		var inviteMsgModal = new bootstrap.Modal(document.getElementById('inviteMsgModal'))
		inviteMsgModal.show()
	})
}

function goHome() {
	let url = '/quiz/modal/goHomeModal';
	$.ajax(url).done(function (modalHtml) {
		$('#modalDiv').html(modalHtml);
		var goHomeMsgModal = new bootstrap.Modal(document.getElementById('goHomeModal'));
		goHomeMsgModal.show();
	})
}

function goQuiz(obj) {

	if (obj == 'prev') {
		let srtNo = parseInt($('#srtNo').val()) - 1;
		if (!srtNo) {
			let url = '/quiz/modal/firstPageModal?srtNo=' + srtNo;
			$.ajax(url).done(function (modalHtml) {
				$('#modalDiv').html(modalHtml);
				var firstPageModal = new bootstrap.Modal(document.getElementById('firstPageModal'));
				firstPageModal.show();
			})
		} else {
			quizAnsSave(srtNo);
		};
	}

	if (obj == 'next') {

		let quizTotalCnt = $('#quizTotalCnt').val();
		let srtNo = parseInt($('#srtNo').val()) + 1;

		if (!$('.active').length) {
			let url = '/quiz/modal/quizNullModal?srtNo=' + srtNo;
			$.ajax(url).done(function (modalHtml) {
				$('#modalDiv').html(modalHtml);
				var quizNullModal = new bootstrap.Modal(document.getElementById('quizNullModal'));
				quizNullModal.show();
			})
		}

		if (srtNo > quizTotalCnt) {
			let url = '/quiz/modal/lastPageModal?srtNo=' + srtNo;
			$.ajax(url).done(function (modalHtml) {
				$('#modalDiv').html(modalHtml);
				var lastPageModal = new bootstrap.Modal(document.getElementById('lastPageModal'));
				lastPageModal.show();
			})
		} else {
			quizAnsSave(srtNo);
		};
	}

	if (obj == 'result') {
		let url = '/quiz/modal/resultPageModal?srtNo=0';
		$.ajax(url).done(function (modalHtml) {
			$('#modalDiv').html(modalHtml);
			var resultPageModal = new bootstrap.Modal(document.getElementById('resultPageModal'));
			resultPageModal.show();
		})
	}
}

function quizAnsSave(srtNo) {
	let url = '/quiz/answer.do';
	let param = $('#quizForm').serialize();
	$.post(url, param, function (result) {
		if (result) {
			movePage(srtNo);
		} else {
			alert("오류가 발생하였습니다.");
		}
	});
}

function movePage(srtNo) {
	let form = $('#quizForm');
	if (!srtNo) { // 결과 페이지
		let duration = Number(getCountTimer());
		let secondsRemaining = $('#count-down-timer').text().split(':');
		let timeSolving = duration - (Number(secondsRemaining[0]) * 60 + Number(secondsRemaining[1]));

		$('#minSolving').val(parseInt(timeSolving / 60));
		$('#secSolving').val(parseInt(timeSolving % 60));

		form.attr('action', '/quiz/result.do');
		form.attr('target', '');
		form.submit();

	} else { // 다음 문제
		var subjectTypeCd = $('#subjectTypeCd').val();
		moveQuiz(subjectTypeCd, srtNo);
	}
}

function moveQuiz(subjectTypeCd, srtNo) {
	$('#subjectTypeCd').val(subjectTypeCd);
	$('#srtNo').val(srtNo);
	let url = '/quiz/quizAjax.do';
	let form = $('#quizForm');
	$.post(url, form.serialize(), function (result) {
		$('i.active').removeClass('active');
		$('#quizDiv').replaceWith(result);
	});
}
function goStatistics(param) {
	let form = $('#' + param);
	form.attr('action', '/quiz/statistics.do');
	form.attr('target', '');
	form.submit();
}

function goQuizMain() {
	let form = $('#startForm');
	form.attr('action', '/quiz/index.do');
	form.attr('target', '');
	form.submit();
}

//카카오 로그인
function kakaoLogin() {
	// Kakao.init('e48108dd0d2c5884764f47d3937cc0d8');
	Kakao.Auth.login({
		success: function (response) {
			Kakao.API.request({
				url: '/v2/user/me',
				success: function (response) {
					let userId = response['kakao_account']['email'];
					if (userId == undefined) {
						var loginMsgModal = new bootstrap.Modal(document.getElementById('loginMsg'))
						loginMsgModal.show()
					} else {
						$('#userId').val(userId);
						goQuizMain();
					}
				},
				fail: function (error) {
					console.log(error)
				},
			})
		},
		fail: function (error) {
			console.log(error)
		},
	})
}

//카카오 로그아웃
function kakaoLogout() {
	Kakao.init('e48108dd0d2c5884764f47d3937cc0d8');
	if (Kakao.Auth.getAccessToken()) {
		Kakao.API.request({
			url: '/v1/user/unlink',
			success: function (response) {
				console.log(response)
				location.replace('/quiz/index.do');
			},
			fail: function (error) {
				console.log(error)
			},
		})
		Kakao.Auth.setAccessToken(undefined)
	}
}

function selectQuiz() {
	let subjectTypeCd = $('#subjectType :selected').val();
	let quizNo = $('#quizNo :selected').val();
	moveQuiz(subjectTypeCd, quizNo);
}