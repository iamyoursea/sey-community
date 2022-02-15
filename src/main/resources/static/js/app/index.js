const main = {
    init: function () {
        const _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-com-save').on('click', function () {
            _this.commentSave();
        });

    },
    save: function () {

        const writeTo = $("body").data("writeTo")

        let formData = new FormData();

        const data = {
            title: $('#input-title').val(),
            author: $('#input-author').val(),
            content: $('#txt-content').val(),
            authorId: $("#user-id").val()
        };

        let fileList = $("#formFile")[0].files;
        for(let i=0; i < fileList.length; i++) {
            formData.append('files', fileList[i]);
        }

        formData.append('key', new Blob([ JSON.stringify(data) ], {type : "application/json"}));

        $.ajax({
            type: "POST",
            url: "/api/v1/" + writeTo,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(formData)
        }).done(() => {
            alert("글이 등록되었습니다.")
            window.location.href = "/" + (writeTo !== "posts" ? writeTo : "")
        }).fail(err => {
            alert(JSON.stringify(err))
        });
    },
    update: function () {
        const writeTo = $("body").data("writeTo")

        const data = {
            title: $('#input-title').val(),
            content: $('#txt-content').val()
        };

        const id = $('#input-id').val();

        $.ajax({
            type: "PUT",
            url: "/api/v1/" + writeTo + "/" + id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(() => {
            alert("글이 수정되었습니다.")
            window.location.href = "/" + (writeTo !== "posts" ? writeTo : "")
        }).fail(err => {
            alert(JSON.stringify(err))
            console.log(err)
        });
    },
    delete: function () {
        const id = $("#input-id").val()

        const Common = {};
        Common.Dialog = {
            /**
             * Dialog Id
             */
            DialogInfo: {
                titleId: 'common-modal-title',
                okBtnId: 'common-modal-ok-btn',
            },

            /**
             * Default Item Message
             */
            Message: {
                title: '알림',
                ok: '확인',
                cancel: '취소'
            },confirm: function(data){
                this.show('confirm', data);
            }
        };

        if(!confirm("정말 이 글을 삭제하시겠습니까?")) {
            return false
       }

        const writeTo = $("body").data("writeTo")

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = "/" + (writeTo !== "posts" ? writeTo : "")
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    commentSave : function () {
        const data = {
            author: $('#comment-author').val(),
            content: $('#comment-content').val(),
            parentId: $('#comment-parentId').val(),
            authorId: $("#user-id").val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts/'+data.parentId+'/comments',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            location.href = '/posts/view/'+data.parentId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
/*
    commentDelete : function () {
        //const id = $('#comment-table').attr('name');
        const id = $('#comment-id').attr('name');

        //const id = $('btn-com-delete').attr('name');

        if(!confirm(id +"번 댓글을 삭제하시겠습니까?")) {
            return false;
        }
        
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/comments/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'

        }).done(function() {
            alert(id +'번 댓글이 삭제되었습니다.');
            window.location.href = window.location.href;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
*/
    commentDelete : function (postId, commentsId) {
        if(!confirm("댓글을 삭제하시겠습니까?")) {
            return false
        }
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+ postId +'/comments/'+commentsId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('댓글이 삭제되었습니다.');
            location.href = '/posts/view/'+postId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

main.init();