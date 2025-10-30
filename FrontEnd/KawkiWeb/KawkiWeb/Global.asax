<%@ Application Language="C#" %>

<script runat="server">
    void Application_Start(object sender, EventArgs e)
    {
        System.Web.UI.ScriptManager.ScriptResourceMapping.AddDefinition(
            "jquery",
            new System.Web.UI.ScriptResourceDefinition
            {
                Path = "~/Scripts/jquery-3.7.1.min.js",
                DebugPath = "~/Scripts/jquery-3.7.1.js"
            }
        );
    }
</script>

