$(function () {
    $('.share').click(function () {
        var json = {
            action: "share",
            params: {
                imageUrl:"http://i9.qhimg.com/t017d891ca365ef60b5.jpg",
                url:"https://www.baidu.com",
                title:"标题",
                text:"描述"
            }
        };

        ec.event(JSON.stringify(json));

    });

    $('.comment').click(function () {

            var json = {
                action: "comment"
            };

            ec.event(JSON.stringify(json));

        });
});