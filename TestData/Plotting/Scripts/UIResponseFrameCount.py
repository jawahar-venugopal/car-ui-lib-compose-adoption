import pandas as pd
import plotly.graph_objects as go
from plotly.subplots import make_subplots

ui_fc_data = [
    ["showDialog", "XML",     126.0, 135.0, 143.0],
    ["showDialog", "Compose",  60.0,  62.5,  67.0],
    ["updateToolbar", "XML",     182.0, 187.0, 197.0],
    ["updateToolbar", "Compose",  55.0,  57.0,  60.0],
]
df_ui_fc = pd.DataFrame(ui_fc_data, columns=["Test", "AppType", "Min", "Median", "Max"])

ui_color_map = {
    ('Min', 'XML'):     '#2196F3',
    ('Median', 'XML'):  '#1565C0',
    ('Max', 'XML'):     '#0D47A1',
    ('Min', 'Compose'): '#43A047',
    ('Median', 'Compose'):'#2E7D32',
    ('Max', 'Compose'): '#1B5E20',
}
ui_stat_app_order = [
    ('Min', 'XML'), ('Median', 'XML'), ('Max', 'XML'),
    ('Min', 'Compose'), ('Median', 'Compose'), ('Max', 'Compose'),
]

def plot_ui_frame_count(df):
    tests = df['Test'].unique()
    fig = go.Figure()
    for stat, app in ui_stat_app_order:
        vals = [df[(df.Test==test)&(df.AppType==app)][stat].iloc[0] for test in tests]
        fig.add_trace(go.Bar(
            x=tests, y=vals,
            name=f"{stat} ({app})",
            marker_color=ui_color_map[(stat,app)],
            text=vals, textposition='auto'
        ))
    fig.update_layout(
        barmode='group', title={
            'text': "<b>UI Response Frame Count (XML vs Compose)</b>",
            'y':0.97, 'x':0.5, 'xanchor': 'center', 'yanchor': 'top', 'font': {'size': 22}
        },
        xaxis_title="UI Response Test", yaxis_title="frameCount",
        legend=dict(orientation='h', y=1.05, x=0.5, xanchor='center', yanchor='bottom'),
        plot_bgcolor='#EDEDED', paper_bgcolor='#FFFFFF', height=400, width=700, font=dict(size=10)
    )
    fig.show()

plot_ui_frame_count(df_ui_fc)