<!DOCTYPE html>

<html lang="en">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
          rel="stylesheet">
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            border: 0;
        }
    </style>
</head>
<body>

<iframe src="/pdfjs/web/viewer.html?file=${baseUrl}${ordinaryUrl}" width="100%"
        frameborder="0"></iframe>

</body>
<script type="text/javascript">
    document.getElementsByTagName('iframe')[0].height = document.documentElement.clientHeight - 10;
    /**
     * 页面变化调整高度
     */
    window.onresize = function () {
        var fm = document.getElementsByTagName("iframe")[0];
        fm.height = window.document.documentElement.clientHeight - 10;
    }
</script>
</html>