<?xml version="1.0" encoding="utf-8"?>
<!--

    Copyright (c) 2000-2009, Jasig, Inc.
    See license distributed with this file and available online at
    https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt

-->
<!-- ========================================================================= -->
<!-- ========== README ======================================================= -->
<!-- ========================================================================= -->
<!--
 | Date: 08/14/2008
 | Author: Matt Polizzotti
 | Company: Unicon,Inc.
 | uPortal Version: uP3.0.0 and uP3.0.1
 |
 | This file determines the non-focused page layout and presentation of the mobile portal.
 | The file is imported by the base stylesheet muniversality.xsl.
 | Parameters and templates from other XSL files may be referenced; refer to muniversality.xsl for the list of parameters and imported XSL files.
 | For more information on XSL, refer to [http://www.w3.org/Style/XSL/].
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<!-- ========================================================================= -->
<!-- ========== TEMPLATE: LAYOUT ============================================= -->
<!-- ========================================================================= -->
<!-- 
| RED
| The layout template controls the structure of two different views, the unathenticated view and the authenticated view.
| The unathenticated view prompts the user to log in. Once logged in, the authenticated view displays the 
| portal's tabs as <h2> headlines. Beneath the headlines, each channel or portlet is rendered as a clickable 
| link held within an <ul> list. The variable $authenticated, which is injected into the xsl using Java, controls 
| which view is displayed. Template contents can be any valid XSL or XHTML.
-->
<xsl:template match="layout">
    <div>
        <xsl:choose>
            <!--START: AUTHENTICATED DIV WRAPPER-->
            <xsl:when test="$AUTHENTICATED='true'">
                <xsl:call-template name="mobile.wrapper" />
            </xsl:when>
            
            <!--START: UNAUTHENTICATED DIV WRAPPER-->
            <xsl:otherwise>
                <xsl:call-template name="mobile.wrapper.guest" />
            </xsl:otherwise>
        </xsl:choose>
        
        <xsl:choose>
            <!--START: AUTHENTICATED VIEW-->
            <xsl:when test="$AUTHENTICATED='true'">                    
                <xsl:call-template name="mobile.header" />
                <xsl:call-template name="mobile.channel.content" />
                <xsl:call-template name="mobile.footer" />
            </xsl:when>
            <!--END: AUTHENTICATED VIEW-->
        
            <!--START: UNAUTHENTICATED VIEW-->
            <xsl:otherwise>
                <xsl:call-template name="mobile.header.public" />
                <xsl:call-template name="mobile.login.public" />
                <xsl:call-template name="mobile.footer" />
            </xsl:otherwise>
            <!--END: UNAUTHENTICATED VIEW-->
        </xsl:choose>
    </div>
</xsl:template>
<!-- ========================================================================= -->


</xsl:stylesheet>