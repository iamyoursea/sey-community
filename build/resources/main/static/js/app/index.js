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
    },
    save: function () {

        const writeTo = $("body").data("writeTo")

        const data = {
            title: $('#input-title').val(),
            author: $('#input-author').val(),
            content: $('#txt-content').val(),
            authorId: $("#user-id").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/v1/" + writeTo,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(() => {
            alert("글이 등록되었습니다.")
            window.location.href = "/" + (writeTo != "posts" ? writeTo : "")
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
            window.location.href = "/" + (writeTo != "posts" ? writeTo : "")
        }).fail(err => {
            alert(JSON.stringify(err))
            console.log(err)
        });
    },
    delete: function () {
        const id = $("#input-id").val()
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
            window.location.href = "/" + (writeTo != "posts" ? writeTo : "")
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();