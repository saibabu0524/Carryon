package com.saibabui.main.presentation.ui.latexutils

import com.saibabui.main.presentation.ui.latexutils.OverleafTemplateParser.buildContentIntervals
import com.saibabui.main.presentation.ui.latexutils.OverleafTemplateParser.extractSections
import com.saibabui.main.presentation.ui.latexutils.OverleafTemplateParser.extractUserCommands
import com.saibabui.main.presentation.ui.latexutils.OverleafTemplateParser.findFieldsPerSection


/**
 * Parses an Overleaf LaTeX template to extract sections and fields for a resume builder app.
 */
object OverleafTemplateParser {

    /**
     * Extracts user-defined commands from the preamble.
     *
     * @param preamble The preamble part of the LaTeX document.
     * @return A list of user-defined command names.
     */
    fun extractUserCommands(preamble: String): List<String> {
        // Updated regex to capture commands with optional arguments
        val pattern = Regex("\\\\newcommand\\{\\\\\\\\([a-zA-Z]+)\\}(?:\\[\\d+\\])?\\{.+\\}")
        return pattern.findAll(preamble).map { it.groups[1]!!.value }.toList()
    }

    /**
     * Finds the end of a balanced brace group starting at a given position.
     *
     * @param text The text to search in.
     * @param start The starting index of the group.
     * @return The index of the closing brace, or -1 if not found.
     */
    fun findGroupEnd(text: String, start: Int): Int {
        var depth = 1 // Start at 1 to account for initial '{'
        var i = start
        while (i < text.length) {
            when (text[i]) {
                '{' -> depth++
                '}' -> depth--
            }
            if (depth == 0) return i
            i++
        }
        return -1
    }

    /**
     * Extracts sections from the document.
     *
     * @param text The document part of the LaTeX template.
     * @return A list of pairs, each containing the section title and the end position of the section command.
     */
    fun extractSections(text: String): List<Pair<String, Int>> {
        val sections = mutableListOf<Pair<String, Int>>()
        var i = 0
        while (i < text.length) {
            if (text.startsWith("\\section{", i)) {
                i += "\\section{".length
                val end = findGroupEnd(text, i)
                if (end != -1) {
                    val title = text.substring(i, end)
                    sections.add(title to (end + 1))
                    i = end + 1
                } else {
                    break
                }
            } else {
                i++
            }
        }
        return sections
    }

    /**
     * Builds content intervals for each section.
     *
     * @param sections The list of sections with their end positions.
     * @param document The document part of the LaTeX template.
     * @return A list of triples, each containing the start, end, and title of a content interval.
     */
    fun buildContentIntervals(
        sections: List<Pair<String, Int>>,
        document: String
    ): List<Triple<Int, Int, String>> {
        val contentIntervals = mutableListOf<Triple<Int, Int, String>>()
        if (sections.isNotEmpty()) {
            val firstContentStart = 0
            val firstContentEnd = sections[0].second
            if (firstContentStart < firstContentEnd) {
                contentIntervals.add(Triple(firstContentStart, firstContentEnd, "main"))
            }
            for (i in 0 until sections.size) {
                val start = sections[i].second
                val end = if (i < sections.size - 1) sections[i + 1].second else document.length
                contentIntervals.add(Triple(start, end, sections[i].first))
            }
        } else {
            contentIntervals.add(Triple(0, document.length, "main"))
        }
        return contentIntervals
    }

    /**
     * Finds fields used within each section.
     *
     * @param document The document part of the LaTeX template.
     * @param userCommands The list of user-defined command names.
     * @param contentIntervals The list of content intervals.
     * @return A map from section titles to lists of fields used in those sections.
     */
    fun findFieldsPerSection(
        document: String,
        userCommands: List<String>,
        contentIntervals: List<Triple<Int, Int, String>>
    ): Map<String, List<String>> {
        val sectionFields = mutableMapOf<String, MutableList<String>>()
        contentIntervals.forEach { sectionFields[it.third] = mutableListOf() }

        for (command in userCommands) {
            // Match command with optional braces: \command or \command{}
            val pattern = Regex("\\\\$command\\b|\\\\$command\\{\\}")
            val matches = pattern.findAll(document).map { it.range.first }.toList()

            for (pos in matches) {
                for ((start, end, title) in contentIntervals) {
                    if (pos in start until end) {
                        sectionFields[title]?.add(command)
                        break
                    }
                }
            }
        }
        return sectionFields.mapValues { it.value.toList() }
    }

    /**
     * Parses the Overleaf template and extracts sections with their associated fields.
     *
     * @param template The full Overleaf LaTeX template.
     * @return A map from section titles to lists of fields.
     */
    fun parseTemplate(template: String): Map<String, List<String>> {
        val preambleEnd = template.indexOf("\\begin{document}")
        if (preambleEnd == -1) return emptyMap()

        val preamble = template.substring(0, preambleEnd)
        val document = template.substring(preambleEnd + "\\begin{document}".length)

        val userCommands = extractUserCommands(preamble)
        val sections = extractSections(document)
        val contentIntervals = buildContentIntervals(sections, document)
        val sectionFields = findFieldsPerSection(document, userCommands, contentIntervals)

        return sectionFields
    }
}

fun main() {
    val template = """
        \documentclass[10pt, letterpaper]{article}

        % Packages:
        \usepackage[
            ignoreheadfoot, % set margins without considering header and footer
            top=2 cm, % seperation between body and page edge from the top
            bottom=2 cm, % seperation between body and page edge from the bottom
            left=2 cm, % seperation between body and page edge from the left
            right=2 cm, % seperation between body and page edge from the right
            footskip=1.0 cm, % seperation between body and footer
            % showframe % for debugging 
        ]{geometry} % for adjusting page geometry
        \usepackage{titlesec} % for customizing section titles
        \usepackage{tabularx} % for making tables with fixed width columns
        \usepackage{array} % tabularx requires this
        \usepackage[dvipsnames]{xcolor} % for coloring text
        \definecolor{primaryColor}{RGB}{0, 79, 144} % define primary color
        \usepackage{enumitem} % for customizing lists
        \usepackage{fontawesome5} % for using icons
        \usepackage{amsmath} % for math
        \usepackage[
            pdftitle={John Doe's CV},
            pdfauthor={John Doe},
            pdfcreator={LaTeX with RenderCV},
            colorlinks=true,
            urlcolor=primaryColor
        ]{hyperref} % for links, metadata and bookmarks
        \usepackage[pscoord]{eso-pic} % for floating text on the page
        \usepackage{calc} % for calculating lengths
        \usepackage{bookmark} % for bookmarks
        \usepackage{lastpage} % for getting the total number of pages
        \usepackage{changepage} % for one column entries (adjustwidth environment)
        \usepackage{paracol} % for two and three column entries
        \usepackage{ifthen} % for conditional statements
        \usepackage{needspace} % for avoiding page brake right after the section title
        \usepackage{iftex} % check if engine is pdflatex, xetex or luatex

        % Ensure that generate pdf is machine readable/ATS parsable:
        \ifPDFTeX
            \input{glyphtounicode}
            \pdfgentounicode=1
            % \usepackage[T1]{fontenc} % this breaks sb2nov
            \usepackage[utf8]{inputenc}
            \usepackage{lmodern}
        \fi



        % Some settings:
        \AtBeginEnvironment{adjustwidth}{\partopsep0pt} % remove space before adjustwidth environment
        \pagestyle{empty} % no header or footer
        \setcounter{secnumdepth}{0} % no section numbering
        \setlength{\parindent}{0pt} % no indentation
        \setlength{\topskip}{0pt} % no top skip
        \setlength{\columnsep}{0cm} % set column seperation
        \makeatletter
        \let\ps@customFooterStyle\ps@plain % Copy the plain style to customFooterStyle
        \patchcmd{\ps@customFooterStyle}{\thepage}{
            \color{gray}\textit{\small John Doe - Page \thepage{} of \pageref*{LastPage}}
        }{}{} % replace number by desired string
        \makeatother
        \pagestyle{customFooterStyle}

        \titleformat{\section}{\needspace{4\baselineskip}\bfseries\large}{}{0pt}{}[\vspace{1pt}\titlerule]

        \titlespacing{\section}{
            % left space:
            -1pt
        }{
            % top space:
            0.3 cm
        }{
            % bottom space:
            0.2 cm
        } % section title spacing

        \renewcommand\labelitemi{${'$'}\circ${'$'}} % custom bullet points
        \newenvironment{highlights}{
            \begin{itemize}[
                topsep=0.10 cm,
                parsep=0.10 cm,
                partopsep=0pt,
                itemsep=0pt,
                leftmargin=0.4 cm + 10pt
            ]
        }{
            \end{itemize}
        } % new environment for highlights

        \newenvironment{highlightsforbulletentries}{
            \begin{itemize}[
                topsep=0.10 cm,
                parsep=0.10 cm,
                partopsep=0pt,
                itemsep=0pt,
                leftmargin=10pt
            ]
        }{
            \end{itemize}
        } % new environment for highlights for bullet entries


        \newenvironment{onecolentry}{
            \begin{adjustwidth}{
                0.2 cm + 0.00001 cm
            }{
                0.2 cm + 0.00001 cm
            }
        }{
            \end{adjustwidth}
        } % new environment for one column entries

        \newenvironment{twocolentry}[2][]{
            \onecolentry
            \def\secondColumn{#2}
            \setcolumnwidth{\fill, 4.5 cm}
            \begin{paracol}{2}
        }{
            \switchcolumn \raggedleft \secondColumn
            \end{paracol}
            \endonecolentry
        } % new environment for two column entries

        \newenvironment{header}{
            \setlength{\topsep}{0pt}\par\kern\topsep\centering\linespread{1.5}
        }{
            \par\kern\topsep
        } % new environment for the header

        \newcommand{\placelastupdatedtext}{% \placetextbox{<horizontal pos>}{<vertical pos>}{<stuff>}
          \AddToShipoutPictureFG*{% Add <stuff> to current page foreground
            \put(
                \LenToUnit{\paperwidth-2 cm-0.2 cm+0.05cm},
                \LenToUnit{\paperheight-1.0 cm}
            ){\vtop{{\null}\makebox[0pt][c]{
                \small\color{gray}\textit{Last updated in September 2024}\hspace{\widthof{Last updated in September 2024}}
            }}}%
          }%
        }%

        % save the original href command in a new command:
        \let\hrefWithoutArrow\href

        % new command for external links:
        \renewcommand{\href}[2]{\hrefWithoutArrow{#1}{\ifthenelse{\equal{#2}{}}{ }{#2 }\raisebox{.15ex}{\footnotesize \faExternalLink*}}}


        \begin{document}
            \newcommand{\AND}{\unskip
                \cleaders\copy\ANDbox\hskip\wd\ANDbox
                \ignorespaces
            }
            \newsavebox\ANDbox
            \sbox\ANDbox{}

            \placelastupdatedtext
            \begin{header}
                \textbf{\fontsize{24 pt}{24 pt}\selectfont John Doe}

                \vspace{0.3 cm}

                \normalsize
                \mbox{{\color{black}\footnotesize\faMapMarker*}\hspace*{0.13cm}Your Location}%
                \kern 0.25 cm%
                \AND%
                \kern 0.25 cm%
                \mbox{\hrefWithoutArrow{mailto:youremail@yourdomain.com}{\color{black}{\footnotesize\faEnvelope[regular]}\hspace*{0.13cm}youremail@yourdomain.com}}%
                \kern 0.25 cm%
                \AND%
                \kern 0.25 cm%
                \mbox{\hrefWithoutArrow{tel:+90-541-999-99-99}{\color{black}{\footnotesize\faPhone*}\hspace*{0.13cm}0541 999 99 99}}%
                \kern 0.25 cm%
                \AND%
                \kern 0.25 cm%
                \mbox{\hrefWithoutArrow{https://yourwebsite.com/}{\color{black}{\footnotesize\faLink}\hspace*{0.13cm}yourwebsite.com}}%
                \kern 0.25 cm%
                \AND%
                \kern 0.25 cm%
                \mbox{\hrefWithoutArrow{https://linkedin.com/in/yourusername}{\color{black}{\footnotesize\faLinkedinIn}\hspace*{0.13cm}yourusername}}%
                \kern 0.25 cm%
                \AND%
                \kern 0.25 cm%
                \mbox{\hrefWithoutArrow{https://github.com/yourusername}{\color{black}{\footnotesize\faGithub}\hspace*{0.13cm}yourusername}}%
            \end{header}

            \vspace{0.3 cm - 0.3 cm}


            \section{Welcome to RenderCV!}



                
                \begin{onecolentry}
                    \href{https://rendercv.com}{RenderCV} is a LaTeX-based CV/resume version-control and maintenance app. It allows you to create a high-quality CV or resume as a PDF file from a YAML file, with \textbf{Markdown syntax support} and \textbf{complete control over the LaTeX code}.
                \end{onecolentry}

                \vspace{0.2 cm}

                \begin{onecolentry}
                    The boilerplate content was inspired by \href{https://github.com/dnl-blkv/mcdowell-cv}{Gayle McDowell}.
                \end{onecolentry}


            
            \section{Quick Guide}

            \begin{onecolentry}
                \begin{highlightsforbulletentries}


                \item Each section title is arbitrary and each section contains a list of entries.

                \item There are 7 unique entry types: \textit{BulletEntry}, \textit{TextEntry}, \textit{EducationEntry}, \textit{ExperienceEntry}, \textit{NormalEntry}, \textit{PublicationEntry}, and \textit{OneLineEntry}.

                \item Select a section title, pick an entry type, and start writing your section!

                \item \href{https://docs.rendercv.com/user_guide/}{Here}, you can find a comprehensive user guide for RenderCV.


                \end{highlightsforbulletentries}
            \end{onecolentry}

            \section{Education}



                
                \begin{twocolentry}{
                    
                    
                \textit{Sept 2000 – May 2005}}
                    \textbf{University of Pennsylvania}

                    \textit{BS in Computer Science}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item GPA: 3.9/4.0 (\href{https://example.com}{a link to somewhere})
                        \item \textbf{Coursework:} Computer Architecture, Comparison of Learning Algorithms, Computational Theory
                    \end{highlights}
                \end{onecolentry}



            
            \section{Experience}



                
                \begin{twocolentry}{
                \textit{Cupertino, CA}    
                    
                \textit{June 2005 – Aug 2007}}
                    \textbf{Software Engineer}
                    
                    \textit{Apple}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item Reduced time to render user buddy lists by 75\% by implementing a prediction algorithm
                        \item Integrated iChat with Spotlight Search by creating a tool to extract metadata from saved chat transcripts and provide metadata to a system-wide search database
                        \item Redesigned chat file format and implemented backward compatibility for search
                    \end{highlights}
                \end{onecolentry}


                \vspace{0.2 cm}

                \begin{twocolentry}{
                \textit{Redmond, WA}    
                    
                \textit{June 2003 – Aug 2003}}
                    \textbf{Software Engineer Intern}
                    
                    \textit{Microsoft}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item Designed a UI for the VS open file switcher (Ctrl-Tab) and extended it to tool windows
                        \item Created a service to provide gradient across VS and VS add-ins, optimizing its performance via caching
                        \item Built an app to compute the similarity of all methods in a codebase, reducing the time from ${'$'}\mathcal{O}(n^2)${'$'} to ${'$'}\mathcal{O}(n \log n)${'$'}
                        \item Created a test case generation tool that creates random XML docs from XML Schema
                        \item Automated the extraction and processing of large datasets from legacy systems using SQL and Perl scripts
                    \end{highlights}
                \end{onecolentry}



            
            \section{Publications}



                
                \begin{samepage}
                    \begin{twocolentry}{
                        Jan 2004
                    }
                        \textbf{3D Finite Element Analysis of No-Insulation Coils}

                        \vspace{0.10 cm}

                        \mbox{Frodo Baggins}, \mbox{\textbf{\textit{John Doe}}}, \mbox{Samwise Gamgee}
                    \end{twocolentry}


                    \vspace{0.10 cm}

                    \begin{onecolentry}
                \href{https://doi.org/10.1109/TASC.2023.3340648}{10.1109/TASC.2023.3340648}
                    \end{onecolentry}
                \end{samepage}


            
            \section{Projects}



                
                \begin{twocolentry}{
                    
                    
                \textit{\href{https://github.com/sinaatalay/rendercv}{github.com/name/repo}}}
                    \textbf{Multi-User Drawing Tool}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item Developed an electronic classroom where multiple users can simultaneously view and draw on a "chalkboard" with each person's edits synchronized
                        \item Tools Used: C++, MFC
                    \end{highlights}
                \end{onecolentry}


                \vspace{0.2 cm}

                \begin{twocolentry}{
                    
                    
                \textit{\href{https://github.com/sinaatalay/rendercv}{github.com/name/repo}}}
                    \textbf{Synchronized Desktop Calendar}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item Developed a desktop calendar with globally shared and synchronized calendars, allowing users to schedule meetings with other users
                        \item Tools Used: C\#, .NET, SQL, XML
                    \end{highlights}
                \end{onecolentry}


                \vspace{0.2 cm}

                \begin{twocolentry}{
                    
                    
                \textit{2002}}
                    \textbf{Custom Operating System}
                \end{twocolentry}

                \vspace{0.10 cm}
                \begin{onecolentry}
                    \begin{highlights}
                        \item Built a UNIX-style OS with a scheduler, file system, text editor, and calculator
                        \item Tools Used: C
                    \end{highlights}
                \end{onecolentry}



            
            \section{Technologies}



                
                \begin{onecolentry}
                    \textbf{Languages:} C++, C, Java, Objective-C, C\#, SQL, JavaScript
                \end{onecolentry}

                \vspace{0.2 cm}

                \begin{onecolentry}
                    \textbf{Technologies:} .NET, Microsoft SQL Server, XCode, Interface Builder
                \end{onecolentry}


            

        \end{document}
    """.trimIndent()

    val sectionFields = OverleafTemplateParser.parseTemplate(template)
    println(sectionFields)
    sectionFields.forEach { (section, fields) ->
        println("Section: $section")
        fields.forEach { field -> println("- $field") }
        print(fields)
    }
}