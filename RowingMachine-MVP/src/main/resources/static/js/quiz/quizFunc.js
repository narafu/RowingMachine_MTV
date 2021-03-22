
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
	let quizPerTime = 60 * 3; // 문제당 시간
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

function login() {
	if (mobileYn) {
		let userId = prompt('이메일을 입력하면 결과가 저장됩니다.\n원치 않을 경우, 공란으로 입력하면, GUEST로 진행됩니다.');
		let form = document.createElement('form');
		form.name = 'startForm';
		form.method = 'post';
		form.action = '/quiz/index.do';
		form.target = '';
		let input = document.createElement('input');
		input.setAttribute('tpye', 'hidden');
		input.setAttribute('name', 'userId');
		input.setAttribute('value', userId);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	} else {
		let url = '/quiz/modal/loginModal';
		$.ajax(url).done(function (modalHtml) {
			$('#modalDiv').html(modalHtml);
			let loginModal = new bootstrap.Modal(document.getElementById('loginModal'))
			loginModal.show();
		})
	}
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
	$('#modalDiv .modal').modal('hide');
	Kakao.Auth.login({
		success: function () {
			Kakao.API.request({
				url: '/v2/user/me',
				success: function (response) {
					let userId = response['kakao_account']['email'];
					$('#userId').val(userId);
					goQuizMain();
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

function trigger(obj, answer) {
	$(obj).toggleClass('active');
	$(obj).siblings().removeClass('active');
	$('#userAnswer').val(answer);
}

function invitation() {
	if (mobileYn) {
		alert($('#userNm').val() + '님 환영합니다.');
	} else {
		let url = '/quiz/modal/invitationModal';
		$.ajax(url).done(function (modalHtml) {
			$('#modalDiv').html(modalHtml);
			let invitationModal = new bootstrap.Modal(document.getElementById('invitationModal'))
			invitationModal.show()
		})
	}
}

function goHome() {
	if (mobileYn) {
		if (confirm('GUEST는 홈으로 가면, 모든 기록이 지워집니다.\n홈으로 이동하시겠습니까?')) {
			location.replace('/quiz/index.do');
		};
	} else {
		let url = '/quiz/modal/goHomeModal';
		$.ajax(url).done(function (modalHtml) {
			$('#modalDiv').html(modalHtml);
			let goHomeMsgModal = new bootstrap.Modal(document.getElementById('goHomeModal'));
			goHomeMsgModal.show();
		})
	}
}

function goQuiz(obj) {

	// if (!$('.active').length) {
	// 	let url = '/quiz/modal/quizNullModal?srtNo=' + srtNo;
	// 	$.ajax(url).done(function (modalHtml) {
	// 		$('#modalDiv').html(modalHtml);
	// 		let quizNullModal = new bootstrap.Modal(document.getElementById('quizNullModal'));
	// 		quizNullModal.show();
	// 	})
	// }

	if (obj == 'prev') {
		let srtNo = parseInt($('#srtNo').val()) - 1;
		let selectedSubjectType = $('#subjectType option:selected').val();
		let firstSubjectType = $('#subjectType option:first').val();
		let selectedqQizNo = $('#quizNo option:selected').val();
		let firstQuizNo = $('#quizNo option:first').val();

		if (selectedSubjectType == firstSubjectType && selectedqQizNo == firstQuizNo) {
			if (mobileYn) {
				alert('첫 번째 문제입니다!');
			} else {
				let url = '/quiz/modal/firstPageModal?srtNo=' + srtNo;
				$.ajax(url).done(function (modalHtml) {
					$('#modalDiv').html(modalHtml);
					let firstPageModal = new bootstrap.Modal(document.getElementById('firstPageModal'));
					firstPageModal.show();
				})
			}
		} else {
			quizAnsSave(srtNo, 'N');
		};
	}

	if (obj == 'next') {
		let srtNo = parseInt($('#srtNo').val()) + 1;
		let selectedSubjectType = $('#subjectType option:selected').val();
		let lastSubjectType = $('#subjectType option:last').val();
		let selectedqQizNo = $('#quizNo option:selected').val();
		let lastQuizNo = $('#quizNo option:last').val();

		if (selectedSubjectType == lastSubjectType && selectedqQizNo == lastQuizNo) {
			if (mobileYn) {
				if (confirm('마지막 문제입니다.\n(풀지 않은 문제는 오답처리 됩니다.)\n결과로 이동하시겠습니?')) {
					quizAnsSave(0, 'N');
				}
			} else {
				let url = '/quiz/modal/lastPageModal?srtNo=0';
				$.ajax(url).done(function (modalHtml) {
					$('#modalDiv').html(modalHtml);
					let lastPageModal = new bootstrap.Modal(document.getElementById('lastPageModal'));
					lastPageModal.show();
				})
			}
		} else {
			if (selectedqQizNo == lastQuizNo) {
				let subjectTypeCd = $('#subjectType option:selected').next().val();
				$('#subjectTypeCd').val(subjectTypeCd);
				srtNo = 1;
			}
			quizAnsSave(srtNo, 'N');
		};
	}

	if (obj == 'result') {
		if (mobileYn) {
			if (confirm('풀지 않은 문제는 오답처리 됩니다.\n결과로 이동하시겠습니까?')) {
				quizAnsSave(0, 'N');
			};
		} else {
			let url = '/quiz/modal/resultPageModal?srtNo=0';
			$.ajax(url).done(function (modalHtml) {
				$('#modalDiv').html(modalHtml);
				let resultPageModal = new bootstrap.Modal(document.getElementById('resultPageModal'));
				resultPageModal.show();
			})
		}
	}
}

function quizAnsSave(srtNo, modalYn) {
	let url = '/quiz/answer.do';
	let param = $('#quizForm').serialize();
	$.post(url, param, function (result) {
		if (result) {
			if (modalYn == 'Y') {
				$('#modalDiv .modal').modal('hide');
			}
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
		let subjectTypeCd = $('#subjectTypeCd').val();
		moveQuiz(subjectTypeCd, srtNo, 'quizForm');
	}
}

function moveQuiz(subjectTypeCd, srtNo, param) {
	$('#subjectTypeCd').val(subjectTypeCd);
	$('#srtNo').val(srtNo);
	let url = '/quiz/quizAjax.do';
	let form = $('#' + param);
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

function selectQuiz(obj) {
	let subjectTypeCd = $('#subjectType :selected').val();
	let quizNo = $('#quizNo :selected').val();
	moveQuiz(subjectTypeCd, quizNo, 'quizForm');
}

function toggleCmntr() {
	$('#cmntrDiv').slideToggle();
	$('#cmntrDiv').scroll();
}