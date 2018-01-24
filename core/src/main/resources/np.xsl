<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="response">
        <html>
            <head>
                <style type="text/css">
                    body{
			            font-size: 14px;
			            font-weight:bolder;
		            }

                    table {
			            border-collapse:collapse;
			            border:solid #999;
			            border-width:1px 0 0 1px;
		            }

		            table caption {font-size:12px;font-weight:bolder;}
		            table th,table td {border:solid #999;border-width:0 1px 1px 0;padding:2px;}
                </style>
            </head>
            <body>
                <p>
                Execution Time: <xsl:value-of select="@executionTime"/>
                </p>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="solution">
        <p>
        ID: <xsl:value-of select="@id"/>
        Total Energy: <xsl:value-of select="@totalEnergyConsumption"/>
        Machine Energy: <xsl:value-of select="@machineEnergyConsumption"/>
        Idle Energy: <xsl:value-of select="@idleEnergyConsumption"/>
        Complete Time: <xsl:value-of select="@completionTime"/>
        <table>
            <tr>
            <td>Machine</td>
            <td>Operation</td>
            <td>Total Energy Consumption</td>
            <td>Machine Energy Consumption</td>
            <td>Idle Energy Consumption</td>
            <td>idle time</td>
            <td>Make Span Time</td>
            </tr>
            <xsl:apply-templates/>
        </table>
        </p>
    </xsl:template>

    <xsl:template match="machine">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td>
                <xsl:for-each select="operation">
                    <b><xsl:value-of select="@id"/></b>
                    (startTime=<xsl:value-of select="@startTime"/>, endTime=<xsl:value-of select="@endTime"/>, energy=<xsl:value-of select="@machineEnergyConsumption"/>)
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each></td>
            <td><xsl:value-of select="@totalEnergyConsumption"/></td>
            <td><xsl:value-of select="@machineEnergyConsumption"/></td>
            <td><xsl:value-of select="@idleEnergyConsumption"/></td>
            <td><xsl:value-of select="@idleTime"/></td>
            <td><xsl:value-of select="@completionTime"/></td>
        </tr>
    </xsl:template>


</xsl:stylesheet>