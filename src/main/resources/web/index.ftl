<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>文件在线预览系统</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
<div class="topbar">
    <div class="wrapper">
        <div class="topbar-wrapper">
            <img height="30" width="30"
                 src="data:image/png;base64,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"
                 alt="Woodpecker UI">&nbsp;&nbsp;
            <a class="link">
                <span>文件在线预览系统 ${version}</span>
            </a>
        </div>
    </div>
</div>

<br/>
<div>
    <div>
        <table style="padding-left: 30px">
            <thead>
            <tr>
                <th colspan="2" style="text-align: left">请输入文件http路径</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <input id="originalurl" type="text" class="urlinput"
                           placeholder="http://127.0.0.1/file/test.docx"
                           value="">
                </td>
                <td>
                    <button class="btn-execute" onclick="doPreview();"> Go
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

    function doPreview() {
        var originalUrl = $("#originalurl").val();
        if (!checkHttp(originalUrl)) {
            alert("必须为http协议地址！");
            return;
        }
        var url = encodeURIComponent(originalUrl);

        var preTab = "${baseUrl}" + "/preview?url=" + url;

        // alert(preTab);
        window.open(preTab);
    }


    function checkHttp(url) {
        var reg = /(http|https):\/\/([\w.]+\/?)\S*/;
        return reg.test(url);
    }


</script>
</body>


</html>

