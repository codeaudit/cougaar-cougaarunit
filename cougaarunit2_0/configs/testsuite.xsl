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
   <head>
   	 <link rel="stylesheet" type="text/css" href="../style.css"></link>
   </head>
   
 	  <table border="0" width="80%" align="center">
 	  	<tr>
 	  		<th colspan="3">Summary</th>
 	  	</tr>
 	  	<tr>
 	  		<td><h4>Tests</h4></td>
 	  		<td><h4>Failures</h4></td>
 	  		<td align="right"><h4>Success rate</h4></td>
 	    </tr>
 	   	<tr>
 	  		<td>
 	  			<xsl:for-each select="testsuite">
				   	 <xsl:value-of select="@tests"/>    	
				</xsl:for-each>
 	  		</td>
 	  		<td>
 	  			<xsl:for-each select="testsuite">
			   	 <xsl:value-of select="@failures"/>    	
			   </xsl:for-each>
 	  		</td>
 	  		<td align="right">
 	  			<xsl:for-each select="testsuite">
				  	 <xsl:value-of select="@successrate"/>    	
				</xsl:for-each>
 	  		</td>
 	  	</tr>
 	  	
 	  	<tr>
 	  		<th colspan="3">Test Cases</th>
 	  	</tr>
 	  	<xsl:for-each select="testsuite/testcase">
	 	  	<tr>
	 	  		<td colspan="3"><h4>
	 	  			<a>
		 	  			<xsl:attribute name="href">
		 	  				<xsl:value-of select="@name"/>.html
		 	  			</xsl:attribute>
	 	  				<xsl:value-of select="@name"/>
 	  				</a>
	 	  			</h4>
	 	  		</td>
 		  	</tr>
 		  	<xsl:for-each select="test">
	 		  	<tr>
	 		  		<td>
	 		  			<h5><xsl:value-of select="command"/></h5>
	 		  		</td>
	 		  		<td><h5>
	 		  			<xsl:value-of select="phase"/>
	 		  			</h5>
	 		  		</td>
	 		  		<td><h5>Result:
	 		  			<xsl:value-of select="result"/>
	 		  			</h5>
	 		  		</td>
	 		  	</tr>
	 		  	
 			</xsl:for-each>
 		</xsl:for-each>
 	 </table>
    
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>