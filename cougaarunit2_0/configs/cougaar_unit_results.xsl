<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <table border="0" width="80%" align="center">
    <xsl:for-each select="TEST">
    	<tr>
    		<td colspan="5">
    			<h3>Cougaar Unit Test: <i><xsl:value-of select="@Name"/></i></h3>
    		</td>
    	</tr>
    </xsl:for-each>
     <tr bgcolor="gray">
      <th align="left">id</th>
      <th align="left">Phase</th>
      <th align="left">Command</th>
      <th align="left">Description</th>
      <th align="left">Result</th>
    </tr>
    <xsl:for-each select="TEST/ID">
    <tr>
      <td><xsl:value-of select="@Value"/></td>
      <td><xsl:value-of select="PHASE"/></td>
      <td><xsl:value-of select="COMMAND"/></td>
      <td><xsl:value-of select="DESCRIPTION"/></td>
      <xsl:if test="RESULT='pass'">
      	<td bgcolor="green"><xsl:value-of select="RESULT"/></td>
      </xsl:if>
      <xsl:if test="RESULT='fail'">
      	<td bgcolor="red"><xsl:value-of select="RESULT"/></td>
      </xsl:if>
      
    </tr>
    </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>


</xsl:stylesheet>