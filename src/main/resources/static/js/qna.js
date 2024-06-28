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