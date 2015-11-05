<%@ page import="model.HolidayType" %>
<%@ page import="model.Country" %>
<%@ page import="java.util.List" %>
<%@ page import="hdalayer.beans.impl.CountryBean" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title></title>
  <script src="../js/calendar.js" type="text/javascript"></script>
</head>
<body>
<%
  String oldHolidayName = request.getParameter("title");
  String oldDate = request.getParameter("date");
  String oldDescription = request.getParameter("description");
%>
<table class="table">
  <tr>
    <h1>
      <%
        if (oldHolidayName != null) out.print("Change");
        else out.print("Add");%> holiday</h1>
    <hr width="98%" border="1" color="#f2f2f0"/>

    <!--Loading images!-->
    <iframe name="iframe" style="position: absolute; left: -9999px;"></iframe>
      <h3>Load an image</h3>
      <input type="file" name="file" size="50" />
      <input type="submit" value="Load">
    </form>

    <form action="addTraditionServlet" method="post">
      <h3>Holiday name</h3>
      <input type="text" name="holidayName" value="<%if (oldHolidayName != null)
           out.print(oldHolidayName);%>" required class="b3radius field addNewsField">

      <h3>Type</h3>
      <select required="3" class="b3radius field" name="holidayType">
        <option disabled>Choose a type</option>
        <%
          HolidayType[] typeList = HolidayType.values();
          for (int i = 0; i < typeList.length; i++) {
          %>
            <option value="<%=(i + 1)%>"><%=typeList[i]%></option>
          <%
          }
        %>
      </select>


      <h3>Date</h3>
      <input type="text" name="holidayDate" value="<%if (oldDate != null)
                    out.print(oldDate);%>" required class="b3radius field addNewsField"
             onfocus="this.select();lcs(this)"
             onclick="event.cancelBubble=true;this.select();lcs(this)">
      - <input type="text" class="b3radius field addNewsField"
               onfocus="this.select();lcs(this)"
               onclick="event.cancelBubble=true;this.select();lcs(this)">

      <h3>Country</h3>
      <select required="3" class="b3radius field" name="holidayType">
        <option disabled>Country</option>
          <%
          List<Country> countryList = new CountryBean().getCountries();
          for (int i = 0; i < countryList.size(); i++) {
          %>
        <option value="<%=(i + 1)%>"><%=countryList.get(i)%></option>
          <%
          }
        %>
        </select>

      <h3>Description</h3>
        <textarea name="traditionDescription" value="<%if (oldDescription != null)
         out.print(oldDescription);%>"
                  class="b3radius field descriptionNewsField" autofocus>
        </textarea>
      <br>
      <div align="center"><button style="width: 50%" class="sb_header b3radius">
        <%if (oldHolidayName != null) out.print("Change holiday"); else
          out.print("Add holiday");%>
      </button></div>
      </form>
  </tr>
</table>

</body>
</html>
