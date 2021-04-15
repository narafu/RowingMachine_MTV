
let mobileYn;

$(function() {
	screenCheck();
})

$(window).resize(function () {
	screenCheck();
});

function screenCheck() {
	if (screen.width > 1200) {
		mobileYn = false;
		console.log('PC 화면입니다.');
	} else if (screen.width < 767) {
		mobileYn = true;
		console.log('모바일 화면입니다.');
	} else {
		mobileYn = true;
		console.log('태블릿 화면입니다.');
	}
}

window.onload = function () {

	let duration = getCountTimer();

	let timer = $('#count-down-timer');
	let min = parseInt(duration / 60);
	let sec = parseInt(duration % 60);

	timer.text(`${paddedFormat(min)}:${paddedFormat(sec)}`);
	startCountDown(--duration, timer);
}

function getCountTimer() {
	let quizTotalCnt = $('.quizNavDiv div').length
	let quizPerTime = 60; // 문제당 시간
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
	// if (mobileYn) {
	// 	let userId = prompt('이메일을 입력하면 결과가 저장됩니다.\n원치 않을 경우, 공란으로 입력하면, \nGUEST로 진행됩니다.');
	// 	if (userId == null) {
	// 		return;
	// 	}
	// 	let form = document.createElement('form');
	// 	form.name = 'startForm';
	// 	form.method = 'post';
	// 	form.action = '/quiz/index.do';
	// 	form.target = '';
	// 	let input = document.createElement('input');
	// 	input.setAttribute('tpye', 'hidden');
	// 	input.setAttribute('name', 'userId');
	// 	input.setAttribute('value', userId);
	// 	form.appendChild(input);
	// 	document.body.appendChild(form);
	// 	form.submit();
	// } else {
		let url = '/quiz/modal/loginModal';
		$.ajax(url).done(function (modalHtml) {
			$('#modalDiv').html(modalHtml);
			let loginModal = new bootstrap.Modal(document.getElementById('loginModal'))
			loginModal.show();
		})
	// }
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
	screenCheck();
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
		if (confirm('GUEST는 홈으로 가면,\n모든 기록이 지워집니다.\n홈으로 이동하시겠습니까?')) {
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

	let quizMstrInfoSeq = $('#quizMstrInfoSeq').val();
	let quizIndex = '';

	$('#sidebar a').each(function(index) {
		if(quizMstrInfoSeq == $(this).siblings('.quizMstrInfoSeq').val()) {
			quizIndex = index;
		}
	})

	if (obj == 'prev') {

		if(quizIndex) {
			quizIndex--;
			let prevQuizMstrInfoSeq = $('#sidebar a:eq(' + quizIndex + ')').siblings('.quizMstrInfoSeq').val();
			quizAnsSave(prevQuizMstrInfoSeq, 'N');
		} else {
			if (mobileYn) {
				alert('첫 번째 문제입니다!');
			} else {
				let url = '/quiz/modal/firstPageModal';
				$.ajax(url).done(function (modalHtml) {
					$('#modalDiv').html(modalHtml);
					let firstPageModal = new bootstrap.Modal(document.getElementById('firstPageModal'));
					firstPageModal.show();
				})
			}
		}

		// let srtNo = parseInt($('#srtNo').val()) - 1;
		// let selectedSubjectType = $('#subjectType option:selected').val();
		// let firstSubjectType = $('#subjectType option:first').val();
		// let selectedqQizNo = $('#quizNo option:selected').val();
		// let firstQuizNo = $('#quizNo option:first').val();
		//
		// if (selectedSubjectType == firstSubjectType && selectedqQizNo == firstQuizNo) {
		// 	if (mobileYn) {
		// 		alert('첫 번째 문제입니다!');
		// 	} else {
		// 		let url = '/quiz/modal/firstPageModal?srtNo=' + srtNo;
		// 		$.ajax(url).done(function (modalHtml) {
		// 			$('#modalDiv').html(modalHtml);
		// 			let firstPageModal = new bootstrap.Modal(document.getElementById('firstPageModal'));
		// 			firstPageModal.show();
		// 		})
		// 	}
		// } else {
		// 	quizAnsSave(srtNo, 'N');
		// };
	}

	if (obj == 'next') {

		let lastQuizMstrInfoSeq = $('#sidebar a:last').siblings('.quizMstrInfoSeq').val();

		if(quizMstrInfoSeq == lastQuizMstrInfoSeq) {
			if (mobileYn) {
				if (confirm('마지막 문제입니다.\n(풀지 않은 문제는 오답처리 됩니다.)\n결과로 이동하시겠습니?')) {
					quizAnsSave(quizMstrInfoSeq, 'N', 'Y');
				}
			} else {
				let url = '/quiz/modal/lastPageModal?quizMstrInfoSeq=' + quizMstrInfoSeq;
				$.ajax(url).done(function (modalHtml) {
					$('#modalDiv').html(modalHtml);
					let lastPageModal = new bootstrap.Modal(document.getElementById('lastPageModal'));
					lastPageModal.show();
				})
			}
		} else {
			quizIndex++;
			let nextQuizMstrInfoSeq = $('#sidebar a:eq(' + quizIndex + ')').siblings('.quizMstrInfoSeq').val();
			quizAnsSave(nextQuizMstrInfoSeq, 'N');
		}

		// let selectedqQizNo = $('#quizNo option:selected').val();
		// let lastQuizNo = $('#quizNo option:last').val();
		// let srtNo = parseInt($('#srtNo').val()) + 1;
		// let selectedSubjectType = $('#subjectType option:selected').val();
		// let lastSubjectType = $('#subjectType option:last').val();

		// if (selectedSubjectType == lastSubjectType && selectedqQizNo == lastQuizNo) {
		// 	if (mobileYn) {
		// 		if (confirm('마지막 문제입니다.\n(풀지 않은 문제는 오답처리 됩니다.)\n결과로 이동하시겠습니?')) {
		// 			quizAnsSave(0, 'N');
		// 		}
		// 	} else {
		// 		let url = '/quiz/modal/lastPageModal?srtNo=0';
		// 		$.ajax(url).done(function (modalHtml) {
		// 			$('#modalDiv').html(modalHtml);
		// 			let lastPageModal = new bootstrap.Modal(document.getElementById('lastPageModal'));
		// 			lastPageModal.show();
		// 		})
		// 	}
		// } else {
		// 	if (selectedqQizNo == lastQuizNo) {
		// 		let subjectTypeCd = $('#subjectType option:selected').next().val();
		// 		$('#subjectTypeCd').val(subjectTypeCd);
		// 		srtNo = 1;
		// 	}
		// 	quizAnsSave(srtNo, 'N');
		// };
	}

	if (obj == 'result') {
		if (mobileYn) {
			if (confirm('풀지 않은 문제는 오답처리 됩니다.\n결과로 이동하시겠습니까?')) {
				quizAnsSave(quizMstrInfoSeq, 'N', 'Y');
			}
		} else {
			let url = '/quiz/modal/lastPageModal?quizMstrInfoSeq=' + quizMstrInfoSeq;
			$.ajax(url).done(function (modalHtml) {
				$('#modalDiv').html(modalHtml);
				let resultPageModal = new bootstrap.Modal(document.getElementById('resultPageModal'));
				resultPageModal.show();
			})
		}
	}
}

function quizAnsSave(quizMstrInfoSeq, modalYn, resultYn) {
	let url = '/quiz/answer.do';
	let param = $('#quizForm').serialize();
	$.post(url, param, function (result) {
		if (result) {
			if (modalYn == 'Y') {
				$('#modalDiv .modal').modal('hide');
			}
			if(resultYn == 'Y') {
				moveResultPage(quizMstrInfoSeq);
			} else {
				movePage(quizMstrInfoSeq);
			}
		} else {
			alert("오류가 발생하였습니다.");
		}
	});
}

function movePage(quizMstrInfoSeq) {
	$('#quizMstrInfoSeq').val(quizMstrInfoSeq);
	let form = $('#quizForm');
	let url = '/quiz/quizAjax.do';
	$.post(url, form.serialize(), function (result) {
		$('i.active').removeClass('active');
		$('#quizDiv').replaceWith(result);
		$('html,body').animate({scrollTop:$('#quizDiv').prev().offset().top}, 100);
		changeSideBar(form);
	});
}

function moveResultPage(quizMstrInfoSeq) {
	$('#quizMstrInfoSeq').val(quizMstrInfoSeq);
	let form = $('#quizForm');
	let duration = Number(getCountTimer());
	let secondsRemaining = $('#count-down-timer').text().split(':');
	let timeSolving = duration - (Number(secondsRemaining[0]) * 60 + Number(secondsRemaining[1]));

	$('#minSolving').val(parseInt(timeSolving / 60));
	$('#secSolving').val(parseInt(timeSolving % 60));

	form.attr('action', '/quiz/result.do');
	form.attr('target', '');
	form.submit();
}

function changeSideBar(form) {
	let url = '/quiz/sidebarAjax.do';
	$.get(url, form.serialize(), function (result) {
		$('#sidebar').html(result);
	})
}

function goStatistics(param) {
	let form = $('#' + param);
	form.attr('action', '/quiz/statistics.do');
	form.attr('target', '');
	form.submit();
}

function toggleCmntr() {
	$('#cmntrDiv').slideToggle();
	$('html,body').animate({scrollTop:$('#cmntrDiv').offset().top}, 100);
}

function certificatePrintPopup() {
	let url = '/quiz/popup/print/certificate.do';
	let name = '예비합격증 인쇄'
	var options = 'top=10, left=10, width=800, height=1000, status=no, menubar=no, toolbar=no, resizable=no';
    window.open(url, name, options);
}

function resultQuizStastics(subjectTypeCd, srtNo) {
	$('#subjectTypeCd').val(subjectTypeCd);
	$('#srtNo').val(srtNo);
	let url = '/quiz/resultQuizStasticsAjax.do';
	let form = $('#ststicsForm');
	$.post(url, form.serialize(), function (result) {
		$('#resultQuizStasticsDiv').replaceWith(result);
		$('html,body').animate({scrollTop:$('#resultQuizStasticsDiv').prev().offset().top}, 100);
	});
}

function eraser(obj) {
	let exCntnt = $(obj).closest('.exDiv').find('.exCntnt');
	let eraseYn = $(obj).closest('.exDiv').find('.eraseYn');
	if(exCntnt.hasClass('text-decoration-line-through')) {
		exCntnt.removeClass('text-decoration-line-through');
		eraseYn.val('N');
	} else {
		exCntnt.addClass('text-decoration-line-through');
		eraseYn.val('Y');
	}
}

function chkAnswer(obj) {
	let quizMstrDtlSeq = $(obj).children('.quizMstrDtlSeq').val();
	if($(obj).hasClass('list-group-item-dark')) {
		$(obj).removeClass('list-group-item-dark');
		$('#userAnswer').val('');
	} else {
		$('.list-group-item-dark').removeClass('list-group-item-dark');
		$(obj).addClass('list-group-item-dark');
		$('#userAnswer').val(quizMstrDtlSeq);
	}
}

function goAdminPage() {
	var form = $('#quizForm');
	var url = '/quiz/admin/quizForm.do';
	form.attr('action', url);
	form.attr('target', '');
	form.submit();
}

function selectQuiz() {
	let url = '/quiz/admin/quizFormAjax.do';
	let data = $('#adminForm').serialize();
	$.post(url, data, function (result) {
		$('#adminForm').replaceWith(result);
	});
}

function saveQuiz() {
	if(!$('[name=answer]:checked').val()) {
		alert("정답을 선택해주세요.");
		return;
	}
	let message = '등록하시겠습니까?';
	let url = '/quiz/admin/regQuiz.do';
	let data = $('#adminForm').serialize();
	goAction(message, url, data);
}

function editQuiz() {
	let message = '수정하시겠습니까?';
	let url = '/quiz/admin/editQuiz.do';
	let data = $('#adminForm').serialize();
	goAction(message, url, data);
}

function delQuiz() {
	let message = '삭제하시겠습니까?';
	let url = '/quiz/admin/delQuiz.do';
	let data = {'quizMstrInfoSeq':$('#quizMstrInfoSeq').val()};
	goAction(message, url, data);
}

function goAction(message, url, data) {
	if(confirm(message)) {
		$.post(url, data, function (result) {
			alert(result);
			location.reload();
		});
	};
}