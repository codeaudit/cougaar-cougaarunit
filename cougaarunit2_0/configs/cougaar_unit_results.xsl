<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
 <html>
  <body>
    <table border="0" width="80%" align="center">
    	<xsl:apply-templates select="test"/>
    </table>
  </body>
 </html>
</xsl:template>


<xsl:template match="test">
    <tr>
      
    	<td colspan="6">
    		<h3>Cougaar Unit Test: <i><xsl:value-of select="@name"/></i></h3>
    	</td>
    </tr>
    
     <tr bgcolor="gray">
      <th align="left">id</th>
      <th align="left">Phase</th>
      <th align="left">Command</th>
      <th align="left">Result</th>
      <th align="left">Expected State</th>
      <th align="left">Actual State</th>
    </tr>
    <xsl:apply-templates select="entry"/>
    
</xsl:template>

<xsl:template match="entry">
    <tr>
      	<td valign="top"><xsl:value-of select="@id"/></td>
	    <td valign="top"><xsl:value-of select="phase"/></td>
        <td valign="top"><xsl:value-of select="command"/></td>
	    <td valign="top"><xsl:value-of select="result"/></td>
      	<td valign="top">
      		<xsl:apply-templates select="actualState"/>
	    </td>
        <td valign="top">
      		<xsl:apply-templates select="expectedState"/>
	    </td>
    </tr>
</xsl:template>

<xsl:template match="expectedState">     
     <table>
     	<tr>
     		<th>action</th>
     		<th>result</th>
     	</tr>
     	<tr>
     		<xsl:apply-templates select="stateChange"/>
     	</tr>
     </table>
</xsl:template>

<xsl:template match="actualState">     
     <table>
     	<tr>
     		<th>action</th>
     		<th>result</th>
     	</tr>
     	<tr>
     		<xsl:apply-templates select="stateChange"/>
     	</tr>
     </table>
</xsl:template>

<xsl:template match="stateChange">     
     	<tr>
      		<td><xsl:value-of select="action"/></td>
      		<td><xsl:value-of select="result"/></td>
      	</tr>
</xsl:template>

      		


</xsl:stylesheet>