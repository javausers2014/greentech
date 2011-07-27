<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
   version='1.0'>
   <xsl:import href="urn:docbkx:stylesheet" />
<xsl:output method="xml" encoding="UTF-8" indent="no"/>

   <fo:declarations>
      <x:xmpmeta xmlns:x="adobe:ns:meta/">
         <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
            <rdf:Description rdf:about="" xmlns:dc="http://purl.org/dc/elements/1.1/">
               <!-- Dublin Core properties go here -->
               <dc:title>Document title</dc:title>
               <dc:creator>Document author</dc:creator>
               <dc:description>Document subject</dc:description>
            </rdf:Description>
            <rdf:Description rdf:about="" xmlns:xmp="http://ns.adobe.com/xap/1.0/">
               <!-- XMP properties go here -->
               <xmp:CreatorTool>Tool used to make the PDF</xmp:CreatorTool>
            </rdf:Description>
         </rdf:RDF>
      </x:xmpmeta>
   </fo:declarations>
   
   <xsl:param name="body.fontset">SimSun</xsl:param>
   <xsl:param name="fop1.extensions" select="1"></xsl:param>
   <xsl:param name="double.sided" select="0"></xsl:param>

   <!-- create front cover -->
   <xsl:template name="book.titlepage.recto">
      <fo:block-container absolute-position="absolute" left="10pt" top="50pt">
         <fo:block>
            <fo:external-graphic src="src/docbkx/resources/background_titlePage_webtop.png" content-height="8cm">
            </fo:external-graphic>
         </fo:block>
      </fo:block-container>
      <fo:block-container absolute-position="absolute" left="40pt" top="460pt">
         <fo:block font-size="20pt">
            <xsl:value-of select="//title" />
         </fo:block>
      </fo:block-container>
      <fo:block-container absolute-position="absolute" left="40pt" top="495pt">
         <fo:block font-size="12pt">
            <xsl:value-of select="//revdescription" />
         </fo:block>
      </fo:block-container>
   </xsl:template>

   <!-- only print toc for books and articles, do not print list of figures or tables etc. -->
   <xsl:param name="generate.toc">
      appendix nop
      article toc,title
      book toc,title
      chapter nop
      part nop
      preface nop
      reference nop
      section nop
   </xsl:param>

   <!-- force header and footer on blank pages -->
   <xsl:param name="headers.on.blank.pages" select="0"></xsl:param>
   <xsl:param name="footers.on.blank.pages" select="0"></xsl:param>

   <xsl:template name="book.titlepage.separator"></xsl:template>

	<xsl:attribute-set name="header.table.properties">
  	<xsl:attribute name="border-bottom-width">12pt</xsl:attribute>
    <xsl:attribute name="border-bottom-style">solid</xsl:attribute>
    <xsl:attribute name="border-bottom-color">#33AACC</xsl:attribute>
	</xsl:attribute-set>

	<xsl:param name="header.rule">0</xsl:param>
	<xsl:param name="header.column.widths">1 1 4</xsl:param>


   <xsl:template name="header.content">
      <xsl:param name="pageclass" select="''" />
      <xsl:param name="sequence" select="''" />
      <xsl:param name="position" select="''" />
      <xsl:param name="gentext-key" select="''" />
    
      <fo:block>
         <xsl:choose>
            <!-- header, odd pages, left: color bar - header, odd pages, right: title of current section -->

            <xsl:when test="$sequence = 'odd' and $position = 'right'">
            	<!-- <fo:retrieve-marker	retrieve-class-name="section.head.marker" 
               										retrieve-position="first-including-carryover"
                  								retrieve-boundary="page-sequence" /> -->
            	<xsl:apply-templates select="." mode="object.title.markup"/>
            </xsl:when>

            <!-- header, even pages, right: color bar - header, even pages, left: title of current section -->
 
            <xsl:when test="$sequence = 'even' and $position = 'left'">
              <!-- <fo:retrieve-marker retrieve-class-name="section.head.marker" 
              										retrieve-position="first-including-carryover"
                  								retrieve-boundary="page-sequence" /> -->
        			<xsl:apply-templates select="." mode="object.title.markup"/>
            </xsl:when>

            <!-- header, first pages (of a chapter), right: no color bar - no left header!
            <xsl:when test="$sequence = 'first' and $position = 'left'">
               <fo:block-container position="absolute" top="-10pt">
                  <fo:block>
                     <fo:external-graphic content-height="2.0cm"
                        src="src/docbkx/resources/schablone_logo.png">
                     </fo:external-graphic>
                  </fo:block>
               </fo:block-container>
            </xsl:when>-->

            <!-- header, blank pages, right: no color bar
            <xsl:when test="$sequence = 'blank' and $position = 'right'">
               <fo:block-container position="absolute" top="12.8pt">
                  <fo:block>
                     <fo:external-graphic content-height="0.435cm"
                        src="src/docbkx/resources/colorbar_right.png">
                     </fo:external-graphic>
                  </fo:block>
               </fo:block-container>
            </xsl:when> -->

         </xsl:choose>
      </fo:block>
   </xsl:template>

  <xsl:attribute-set name="footer.table.properties">
  	<xsl:attribute name="border-top-width">4pt</xsl:attribute>
    <xsl:attribute name="border-top-style">solid</xsl:attribute>
    <xsl:attribute name="border-top-color">#33AACC</xsl:attribute>
	</xsl:attribute-set>

	<xsl:param name="footer.rule">0</xsl:param>


   <xsl:template name="footer.content">
      <xsl:param name="pageclass" select="''" />
      <xsl:param name="sequence" select="''" />
      <xsl:param name="position" select="''" />
      <xsl:param name="gentext-key" select="''" />

      <!-- include release info in footer
      <xsl:variable name="ReleaseInfo">
         <xsl:choose>
            <xsl:when test="//revhistory/revision[1]/revnumber">
               <xsl:text>Dynamic Application Framework </xsl:text>
               <xsl:value-of select="//revhistory/revision[1]/revnumber" />
               <xsl:text> for Visual Rules 4.4</xsl:text>
            </xsl:when>
            <xsl:otherwise>
            </xsl:otherwise>
         </xsl:choose>
      </xsl:variable> -->

      <xsl:choose>
         <xsl:when test="$pageclass='titlepage'">
         </xsl:when>

         <!-- footer, even pages, left: page numbering - footer, even pages, right: release info -->
         <xsl:when test="$sequence = 'even' and $position='left'">
            <fo:page-number />
         </xsl:when>
         <!-- <xsl:when test="$sequence = 'even' and $position='right'">
            <xsl:value-of select="$ReleaseInfo" />
         </xsl:when> -->

         <!-- footer, odd pages, left: release info - footer, odd pages, right: page numbering -->
         <xsl:when test="$sequence = 'odd' and $position='right'">
            <fo:page-number />
         </xsl:when>

         <!--  footer, first pages (of a chapter), right: release info - footer, first pages, left: page numbering -->
         <xsl:when test="$sequence = 'first' and $position='left'">
            <fo:page-number />
         </xsl:when>

         <!-- footer, blank pages, left: page numbering -->
         <xsl:when test="$sequence = 'blank' and $position = 'left'">
            <fo:page-number />
         </xsl:when>

         <xsl:otherwise>
         </xsl:otherwise>

      </xsl:choose>
   </xsl:template>

   <!-- don't display url after link -->
   <xsl:param name="ulink.show" select="0"></xsl:param>

   <!-- number unlabeled sections -->
   <xsl:param name="section.autolabel" select="1"></xsl:param>
   <xsl:param name="section.label.includes.component.label" select="1"></xsl:param>

   <!-- keep table row contents together, but allow tables to be split and cell padding -->
   <xsl:param name="keep.row.together">1</xsl:param>

   <xsl:template match="row">
      <xsl:param name="spans" />

      <fo:table-row>
         <xsl:if test="$keep.row.together != '0'">
            <xsl:attribute name="keep-together.within-page">always</xsl:attribute>
         </xsl:if>
         <xsl:call-template name="anchor" />

         <xsl:apply-templates select="(entry|entrytbl)[1]">
            <xsl:with-param name="spans" select="$spans" />
         </xsl:apply-templates>
      </fo:table-row>

      <xsl:if test="following-sibling::row">
         <xsl:variable name="nextspans">
            <xsl:apply-templates select="(entry|entrytbl)[1]" mode="span">
               <xsl:with-param name="spans" select="$spans" />
            </xsl:apply-templates>
         </xsl:variable>

         <xsl:apply-templates select="following-sibling::row[1]">
            <xsl:with-param name="spans" select="$nextspans" />
         </xsl:apply-templates>
      </xsl:if>
   </xsl:template>

   <xsl:attribute-set name="table.properties" use-attribute-sets="formal.object.properties">
      <xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
   </xsl:attribute-set>

   <!-- table cell padding -->

   <xsl:attribute-set name="table.cell.padding">
      <xsl:attribute name="padding-left">5pt</xsl:attribute>
      <xsl:attribute name="padding-right">5pt</xsl:attribute>
      <xsl:attribute name="padding-top">5pt</xsl:attribute>
      <xsl:attribute name="padding-bottom">5pt</xsl:attribute>
   </xsl:attribute-set>

   <xsl:param name="collect.xref.targets">
      yes
   </xsl:param>

     <xsl:template match="section|sect1|sect2|sect3|sect4|sect5"
    mode="object.title.markup">
    <fo:block font-family="${body.font.family}" margin-left="{$title.margin.left}" margin-bottom="3cm" white-space-collapse="false"/>
  </xsl:template>
   
   
   <!-- insert hard page-break -->
   <xsl:template match="processing-instruction('hard-pagebreak')">
      <fo:block break-after='page' />
   </xsl:template>

   <!-- customize space between numbering in an 
        item of an ordered list and the text (content) of this item -->
   <xsl:param name="orderedlist.label.width">
      2.0em
   </xsl:param>

   <!-- suppress spaces before/after indexterm in pdf output -->
   <xsl:strip-space elements="indexterm" />

   <xsl:template match="beginpage">
      <fo:block break-after="page" />
   </xsl:template>
   
  <!-- Add graphics to "Tip"/"Caution"/"Note"/"Show"-sections -->
  <xsl:param name="admon.graphics" select="1"/>
  <xsl:param name="admon.graphics.path">src/docbkx/resources/</xsl:param>
  <xsl:template match="*" mode="admon.graphic.width">
  	<xsl:text>48pt</xsl:text>
  </xsl:template>
  <xsl:attribute-set name="admonition.properties">
  	<xsl:attribute name="border-top">0.5pt solid black</xsl:attribute>
  </xsl:attribute-set>

  <!-- Format sidebars -->
	<xsl:attribute-set name="sidebar.properties">
  	<xsl:attribute name="border-top">5pt solid #22AACC</xsl:attribute>
  	<xsl:attribute name="border-bottom">1pt solid #BCBDBC</xsl:attribute>
  	<xsl:attribute name="border-left">1pt solid #BCBDBC</xsl:attribute>
  	<xsl:attribute name="border-right">1pt solid #BCBDBC</xsl:attribute>
    <xsl:attribute name="background-color">#ffffff</xsl:attribute>
  </xsl:attribute-set>
  
  <!-- Format figures: title centered and below the figure --> 
  <xsl:template match="title">
    	<xsl:if test="parent::figure">
    		<xsl:attribute name="text-align">center</xsl:attribute>
      </xsl:if>
	</xsl:template>
  
	<xsl:template match="figure">
	  <xsl:variable name="id">
	    <xsl:call-template name="object.id"/>
	  </xsl:variable>
	
	  <fo:block id="{$id}"
	            xsl:use-attribute-sets="formal.object.properties">
	    <xsl:apply-templates/>
	    <xsl:call-template name="formal.object.heading"/>
	  </fo:block>
	</xsl:template>
	
	<!-- Ensure that elements tagged with <?mansour-fo keep-together?> are kept together -->
	<xsl:template match="*[processing-instruction('mansour-fo') = 'keep-together']"> 
    <fo:block keep-together.within-column="always"> 
      <xsl:apply-imports/> 
    </fo:block> 
  </xsl:template> 

	<!-- Underline xref-Elements (cross-references in pdf) -->
	<xsl:attribute-set name="xref.properties">
  	<!-- <xsl:attribute name="text-decoration"> -->
  	<xsl:attribute name="color">
     	<xsl:choose>
      	<xsl:when test="self::ulink and @url">#999999</xsl:when>
      	<xsl:when test="self::xref and @linkend">#7F7F7F</xsl:when>
				<xsl:otherwise>black</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
  </xsl:attribute-set>

</xsl:stylesheet>