[#ftl]
<!DOCTYPE HTML>
<html>

<head>
    <title>Committers for ${project.repository.url}</title>
    <link rel="stylesheet" href="default.css"/>
</head>

<body>
<div id="main">
    <h1><a href="${project.repository.url}">${project.repository.projectName}</a></h1>
    <div class="link">${project.repository.url}</a></div><p>

    Number of commits per month by developer, capped at ${cap} commits per month, between ${startDate} and ${endDate}.<p>

    <table id="developer-table">
[#list developers as developer]
        <tr>
            <td>
                ${developer.name}<br>
                <small>${developer.numberOfCommits} commits, ${developer.numberOfRecentCommits} recent</small>
            </td>
            <td>
                <img src="sparkline-${developer.name}.png" alt="sparkline for ${developer.name}">
            </td>
        </tr>
[/#list]
    </table>
</div>
</body>

</html>


