import pandas as pd
import plotly.graph_objects as go

data = [
    ['MainActivity',         'Cold', 'XML',      635.3, 731.7, 1017.3],
    ['MainActivity',         'Cold', 'Compose',  820.9,  891.3, 1109.9],
    ['MainActivity',         'Warm', 'XML',      206.0,  249.0,  314.8],
    ['MainActivity',         'Warm', 'Compose',  224.3,  252.6,  286.5],
    ['MainActivity',         'Hot',  'XML',      158.1,  226.2,  312.6],
    ['MainActivity',         'Hot',  'Compose',   96.7,  131.6,  193.9],

    ['ToolbarActivity',      'Cold', 'XML',     669.0, 784.7, 944.2],
    ['ToolbarActivity',      'Cold', 'Compose', 844.9, 933.6, 1038.7],
    ['ToolbarActivity',      'Warm', 'XML',      224.7,  255.6,  301.6],
    ['ToolbarActivity',      'Warm', 'Compose',  240.2,  284.6,  310.8],
    ['ToolbarActivity',      'Hot',  'XML',      128.8,  181.2,  232.8],
    ['ToolbarActivity',      'Hot',  'Compose',  94.2,  138.8,  185.5],

    ['PreferenceActivity',   'Cold', 'XML',     631.2, 768.6, 1049.9],
    ['PreferenceActivity',   'Cold', 'Compose', 645.1, 785.9, 1316.4],
    ['PreferenceActivity',   'Warm', 'XML',      188.2,  209.1,  378.8],
    ['PreferenceActivity',   'Warm', 'Compose', 211.9, 269.5, 359.4],
    ['PreferenceActivity',   'Hot',  'XML',      139.9,  205.2,  277.3],
    ['PreferenceActivity',   'Hot',  'Compose',  110.4, 151.0, 198.1],

    ['DialogsActivity',      'Cold', 'XML',     705.1, 808.7, 1336.2],
    ['DialogsActivity',      'Cold', 'Compose', 847.3, 979.6, 1364.6],
    ['DialogsActivity',      'Warm', 'XML',      207.5,  243.9,  295.8],
    ['DialogsActivity',      'Warm', 'Compose',  240.4,  285.8,  345.0],
    ['DialogsActivity',      'Hot',  'XML',      119.8,  144.3,  178.0],
    ['DialogsActivity',      'Hot',  'Compose',  91.9,  120.5, 158.1],

    ['CarUiListItemActivity', 'Cold', 'XML',    708.4, 796.5, 1129.2],
    ['CarUiListItemActivity', 'Cold', 'Compose',888.5, 995.7, 1418.0],
    ['CarUiListItemActivity', 'Warm', 'XML',     238.9,  276.4,  438.4],
    ['CarUiListItemActivity', 'Warm', 'Compose', 290.9,  369.4,  470.6],
    ['CarUiListItemActivity', 'Hot',  'XML',     146.1,  205.1,  308.2],
    ['CarUiListItemActivity', 'Hot',  'Compose', 137.9,  188.5,  257.3],

    ['CarUiRecyclerViewActivity', 'Cold', 'XML',     636.0,  709.4,  967.1],
    ['CarUiRecyclerViewActivity', 'Cold', 'Compose',830.6, 966.6, 1183.1],
    ['CarUiRecyclerViewActivity', 'Warm', 'XML',     211.1,  240.8,  293.7],
    ['CarUiRecyclerViewActivity', 'Warm', 'Compose', 223.4,  268.0,  368.0],
    ['CarUiRecyclerViewActivity', 'Hot',  'XML',     112.4,  141.6,  229.6],
    ['CarUiRecyclerViewActivity', 'Hot',  'Compose', 93.2,  129.3,  174.5],

    ['GridCarUiRecyclerViewActivity', 'Cold', 'XML',    637.7, 689.0, 1256.1],
    ['GridCarUiRecyclerViewActivity', 'Cold', 'Compose', 851.6,  943.2, 1448.9],
    ['GridCarUiRecyclerViewActivity', 'Warm', 'XML',     256.1,  302.7,  375.1],
    ['GridCarUiRecyclerViewActivity', 'Warm', 'Compose', 261.5,  301.1,  367.0],
    ['GridCarUiRecyclerViewActivity', 'Hot',  'XML',     132.1,  186.9,  235.1],
    ['GridCarUiRecyclerViewActivity', 'Hot',  'Compose',  96.8,  139.4,  209.6],
]

df = pd.DataFrame(data, columns=['Activity', 'Mode', 'AppType', 'Min', 'Median', 'Max'])

def plot_six_bars_per_activity(df, mode="Cold"):
    df = df[df['Mode'] == mode]
    activities = df['Activity'].unique()
    stat_app_order = [
        ('Min', 'XML'), 
        ('Median', 'XML'), 
        ('Max', 'XML'), 
        ('Min', 'Compose'), 
        ('Median', 'Compose'), 
        ('Max', 'Compose'),
    ]

    color_map = {
        ('Min', 'XML'): '#2196F3',      
        ('Median', 'XML'): '#1565C0',   
        ('Max', 'XML'): '#0D47A1',      
        ('Min', 'Compose'): '#43A047',  
        ('Median', 'Compose'): '#2E7D32', 
        ('Max', 'Compose'): '#1B5E20',  
    }

    x_labels = []
    bar_vals = {key: [] for key in stat_app_order}

    for activity in activities:
        for stat, app in stat_app_order:
            row = df[(df['Activity'] == activity) & (df['AppType'] == app)]
            val = float(row[stat]) if not row.empty else None
            bar_vals[(stat, app)].append(val)
        x_labels.append(activity)

    fig = go.Figure()
    for (stat, app) in stat_app_order:
        fig.add_trace(go.Bar(
            x=x_labels,
            y=bar_vals[(stat, app)],
            name=f"{stat} ({app})",
            marker_color=color_map[(stat, app)],
            text=bar_vals[(stat, app)],
            textposition='auto'
        ))

    fig.update_layout(
        barmode='group',
        title=f"{mode} Startup Time (XML vs Compose)",
        xaxis_title="Activity",
        yaxis_title="timeToInitialDisplayMs",
        font=dict(size=9),
        legend_title="Legend",
        height=400,
        width=1100,
        bargap=0.16,
        bargroupgap=0.06,
        plot_bgcolor="#EDEDED",
        paper_bgcolor="#FFFFFF",
        legend=dict(
            orientation="h",
            yanchor="bottom",
            y=1.02,
            xanchor="right",
            x=1
        ),
    )
    fig.show()

plot_six_bars_per_activity(df, mode="Cold")
plot_six_bars_per_activity(df, mode="Warm")
plot_six_bars_per_activity(df, mode="Hot")
