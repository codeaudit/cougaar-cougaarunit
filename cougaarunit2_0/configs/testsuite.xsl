<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
   <title>Test Suite Results for
   	 <xsl:for-each select="testsuite">
    	 <xsl:value-of select="@name"/>    	
    </xsl:for-each>
   </title>
 	  <table border="0" width="80%" align="center">
 	  	<tr>
 	  		<td>Summary</td>
 	  	</tr>
 	  	<tr>
 	  		<td>
 	  			<table>
 	  				<tr>
 	  					<td>Tests</td>
 	  					<td>Failures</td>
 	  					<td>Success rate</td>
 	  				</tr>
 	  				<tr>
 	  					<td></td>
 	  					<td></td>
 	  					<td></td>
 	  				</tr>
 	  			</table>
 	  		</td>
 	  	</tr>
 	  	<tr>
 	  		<td>Test Cases</td>
 	  	</tr>
 	  	<xsl:for-each select="testsuite/testcase">
	 	  	<tr>
	 	  		<td><xsl:value-of select="@name"/></td>
 		  	</tr>
 		  	<xsl:for-each select="test">
	 		  	<tr>
	 		  		<td>
	 		  			<table>
	 		  				<tr>
	 		  					<td>
	 		  						<xsl:value-of select="command"/>
	 		  					</td>
	 		  					<td>
	 		  						<xsl:value-of select="phase"/>
	 		  					</td>
	 		  					<td>
	 		  						<xsl:value-of select="description"/>
	 		  					</td>
	 		  					<td>
	 		  						<xsl:value-of select="result"/>
	 		  					</td>
	 		  				</tr>
	 		  			</table>
	 		  		</td>
 			  	</tr>
 			</xsl:for-each>
 		</xsl:for-each>
 	 </table>
    
  </body>
  </html>
</xsl:template>


</xsl:stylesheet>