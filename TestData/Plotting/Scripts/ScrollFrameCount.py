import pandas as pd
import plotly.graph_objects as go
import plotly.express as px

fc_data = [
    ['CarUiListItemActivity',        'XML',     535.0, 550.5, 563.0],
    ['CarUiListItemActivity',        'Compose', 127.0, 134.5, 142.0],
    ['GridCarUiRecyclerViewActivity','XML',     204.0, 211.0, 220.0],
    ['GridCarUiRecyclerViewActivity','Compose', 106.0, 109.0, 113.0],
    ['CarUiRecyclerViewActivity',    'XML',     321.0, 328.5, 337.0],
    ['CarUiRecyclerViewActivity',    'Compose',  61.0,  65.0,  70.0],
    ['PreferencesActivity',          'XML',     358.0, 364.0, 370.0],
    ['PreferencesActivity',          'Compose',  51.0,  53.0,  56.0],
]
df_fc = pd.DataFrame(fc_data, columns=['Activity','AppType','Min','Median','Max'])

color_map = {
    ('Min', 'XML'):     '#2196F3',
    ('Median', 'XML'):  '#1565C0',
    ('Max', 'XML'):     '#0D47A1',
    ('Min', 'Compose'): '#43A047',
    ('Median', 'Compose'):'#2E7D32',
    ('Max', 'Compose'): '#1B5E20',
}
stat_app_order = [
    ('Min', 'XML'), ('Median', 'XML'), ('Max', 'XML'),
    ('Min', 'Compose'), ('Median', 'Compose'), ('Max', 'Compose'),
]

def plot_frame_count(df):
    activities = df['Activity'].unique()
    fig = go.Figure()
    for stat, app in stat_app_order:
        vals = [df[(df.Activity==act)&(df.AppType==app)][stat].iloc[0] for act in activities]
        fig.add_trace(go.Bar(
            x=activities, y=vals,
            name=f"{stat} ({app})",
            marker_color=color_map[(stat,app)],
            text=vals, textposition='auto'
        ))
    fig.update_layout(
        barmode='group', title={
            'text': f"<b>Scroll Test Frame Count (XML vs Compose)</b>",
            'y':0.97,
            'x':0.5,
            'xanchor': 'center',
            'yanchor': 'top'
        },
        xaxis_title="Activity", yaxis_title="frameCount",
        legend=dict(orientation='h', y=1.02, x=0.5, xanchor='center', yanchor='bottom'),
        plot_bgcolor='#EDEDED', paper_bgcolor='#FFFFFF', height=500, width=740, font=dict(size=10)
    )
    fig.show()

plot_frame_count(df_fc)
