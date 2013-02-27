    <%@ page contentType="text/html;charset=UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}" />
        <html>
        <head>
        <SCRIPT>
        $(document).ready(function(){
        dateInit();
        dialogInit();
        });
        function setText(text){
        //alert(text)
        $('#log').val(text);
        }
        function addLog(responseText, statusText, xhr, $form){
        setText(responseText.data);
        }
        function search(success,url){
        options['success']=success;
        options['url']='<%=request.getContextPath()%>'+url;
        $('#myForm').ajaxSubmit(options);
        }
        </SCRIPT>
        </head>
        <body>


        <form id="myForm">


        选择查询时间<input type="text" id="from" name="from" class="time_from"
        placeholder="start " /> <input type="text" id="to" name="to"
        class="time_to" placeholder="end " />
        <input type="text" name="key" placeholder="关键字 " />
        </br>
        <input type="text" name="aid" placeholder="按照位置查询 " />
        <input type="text" name="uid" placeholder="按照玩家查询" />
        <input type="button"
        value="查询" onclick="search(addLog,'/bg_log/search')" />

        <input type="button"
        value="检测竞技场" onclick="search(addLog,'/bg_log/check')" />
        <input type="button"
        value="关闭/开启竞技场" onclick="search(addLog,'/bg_log/open')" />
        </form>
        <textarea rows="10" id="log" name="script"></textarea>
        </body>
        </html>