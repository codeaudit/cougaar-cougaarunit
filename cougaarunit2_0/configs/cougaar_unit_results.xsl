<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
    <table border="0" width="80%" align="center">
    <xsl:for-each select="test">
    	<tr>
    		<td colspan="5">
    			<h3>Cougaar Unit Test: <i><xsl:value-of select="@name"/></i></h3>
    		</td>
    	</tr>
    </xsl:for-each>
     <tr bgcolor="gray">
      <th align="left">id</th>
      <th align="left">Phase</th>
      <th align="left">Command</th>
      <th align="left">Result</th>
      <th align="left">Expected State</th>
      <th align="left">Actual State</th>
    </tr>
    <xsl:for-each select="test/entry">
    <tr>
      <td><xsl:value-of select="@id"/></td>
      <td><xsl:value-of select="phase"/></td>
      <td><xsl:value-of select="command"/></td>
      <td><xsl:value-of select="result"/></td>
     
      <td>
      	<xsl:for-each select="test/entry/expectedState">
      		<table>
      			<tr>
      				<th>action</th>
      				<th>result</th>
      			</tr>
      			<xsl:for-each select="test/entry/expectedState/stateChange">
      			<tr>
      				<td><xsl:value-of select="action"/></td>
      				<td><xsl:value-of select="result"/></td>
      			</tr>
      			</xsl:for-each>
      		</table>
      	</xsl:for-each>
      </td>
      <td>
     	<xsl:for-each select="test/entry/actualState">
      		<table>
      			<tr>
      				<th>action</th>
      				<th>result</th>
      			</tr>
      			<xsl:for-each select="test/entry/actualState/stateChange">
      			<tr>
      				<td><xsl:value-of select="action"/></td>
      				<td><xsl:value-of select="result"/></td>
      			</tr>
      			</xsl:for-each>
      		</table>
      	</xsl:for-each>
      </td>
    
    </tr>
    </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>


</xsl:stylesheet>