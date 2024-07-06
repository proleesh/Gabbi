/*
author: Sung-Hyuk Lee
date: 2024.6.24 ~ 2024.7.5
 */

const deleteButton = document.getElementById('delete-btn');
const modifyButton = document.getElementById('modify-btn');
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', () => {
        fetch('/api/qna-all', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                qnaTitle: document.getElementById("qnaTitle").value,
                qnaContent: document.getElementById("qnaContent").value
            }),
        }).then(() => {
            alert('문의 글 등록 처리 완료!')
            location.replace("/qna-all")
        }).catch(e => console.error(e));
    })
}

if (modifyButton) {
    modifyButton.addEventListener('click', () => {
        const params = new URLSearchParams(location.search);
        const id = params.get('id');
        fetch(`/api/qna-all/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                qnaTitle: document.getElementById("qnaTitle").value,
                qnaContent: document.getElementById("qnaContent").value
            })
        }).then(() => {
            alert("등록글 수정 처리 완료")
            location.replace(`/qna-all/${id}`)
        })
    })
}
if (deleteButton) {
    deleteButton.addEventListener('click', () => {
        let id = document.getElementById("qna-id").value
        fetch(`/api/qna-all/${id}`, {
            method: 'DELETE',
        }).then(() => {
            alert("등록글 삭제 처리 성공")
            location.replace(`/qna-all`)
        })
    })
}
document.addEventListener('DOMContentLoaded', function () {
    const submitCommentButton = document.getElementById('submit-comment-btn');
    if (submitCommentButton) {
        submitCommentButton.addEventListener('click', function (event) {
            console.error('Reply button clicked')
            event.preventDefault(); // 기본 양식 제출 동작 차단

            const content = document.getElementById('new-comment-content').value;
            const qnaId = document.getElementById('qna-id').value;
            if (content.trim() === '') {
                alert('댓글 내용을 입력하세요.');
                return;
            }

            fetch('/api/comments', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({content: content, qnaId: qnaId})
            }).then(response => {
                if (response.ok) {
                    return response.json(); // JSON 형식의 응답 데이터를 반환합니다.
                } else {
                    throw new Error('Failed to add comment');
                }
            }).then(data => {
                // 필요에 따라 새 댓글, 샘플 코드를 추가하여 메시지 목록이나 기타 콘텐츠를 업데이트한 후 페이지를 업데이트합니다.
                console.log('New comment added:', data);
                location.reload(); // 댓글 목록을 업데이트하는 로직을 교체
            }).catch(error => {
                console.error('Error adding comment:', error);
                alert('댓글을 추가할려면 로그인하세요.');
                location.replace("/members/login")
            });
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    // 답글 event
    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('reply-btn')) {
            // 상위 댓글 요소 찾기
            const comment = event.target.closest('.comment');
            // 답장 form 찾기
            const replyForm = comment.querySelector('.reply-form');
            // 답장 from 표시
            replyForm.style.display = 'block';
        }
            event.preventDefault();

        // 답글 달기 처리
        if (event.target.classList.contains('submit-reply-btn')) {

            const comment = event.target.closest('.comment');
            const replyContent = event.target.parentElement.querySelector('.reply-content').value;
            console.log(replyContent)
            const commentId = comment.getAttribute('data-comment-id');

            if (replyContent.trim() === '') {
                alert('답글 내용을 입력하세요.');
                return;
            }

            fetch('/api/replies', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({content: replyContent, commentId: commentId})
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Failed to add reply');
                }
            }).then(data => {
                console.log('New reply added:', data);
                location.reload();
            }).catch(error => {
                console.error('Error adding reply:', error);
                alert('답글을 추가하는데 실패했습니다.');
            });
        }
    });
});