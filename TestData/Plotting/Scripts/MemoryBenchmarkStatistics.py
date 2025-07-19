!pip install plotly --quiet

import plotly.graph_objects as go
import pandas as pd

data = {
    'Metric': ['lastSize']*6 + ['maxSize']*6,
    'Type': ['Min', 'Median', 'Max', 'Min', 'Median', 'Max']*2,
    'System': ['XML', 'XML', 'XML', 'Compose', 'Compose', 'Compose']*2,
    'HeapSize': [
        7767.0, 7939.5, 8029.0,    # XML lastSize
        5859.0, 7295.0, 9212.0,    # Compose lastSize
        10307.0, 10347.5, 10407.0, # XML maxSize
        11092.0, 11626.0, 11747.0, # Compose maxSize
    ],
    'RssAnon': [
        74792.0, 75008.0, 75388.0,    # XML lastSize
        70776.0, 72450.0, 74444.0,    # Compose lastSize
        76004.0, 76466.0, 77112.0,    # XML maxSize
        78844.0, 79382.0, 81224.0,    # Compose maxSize
    ]
}
df = pd.DataFrame(data)

color_map = {
    ('Min', 'XML'): '#2196F3',      # Blue 500
    ('Median', 'XML'): '#1565C0',   # Blue 800
    ('Max', 'XML'): '#0D47A1',      # Blue 900
    ('Min', 'Compose'): '#43A047',  # Green 600
    ('Median', 'Compose'): '#2E7D32', # Green 800
    ('Max', 'Compose'): '#1B5E20',  # Green 900
}
legend_order = [
    ('Min', 'XML'),
    ('Median', 'XML'),
    ('Max', 'XML'),
    ('Min', 'Compose'),
    ('Median', 'Compose'),
    ('Max', 'Compose')
]

def plot_metric(df, value_col, title):
    fig = go.Figure()
    for (typ, sys) in legend_order:
        plot_df = df[(df['Type'] == typ) & (df['System'] == sys)]
        fig.add_trace(go.Bar(
            x=plot_df['Metric'],
            y=plot_df[value_col],
            name=f"{typ} {sys}",
            marker_color=color_map[(typ, sys)],
            text=[f"{int(y):,}" for y in plot_df[value_col]],
            textposition='inside',
            insidetextanchor='end',
            textfont_size=9
        ))
    fig.update_layout(
        width=600,
        height=350,
        barmode='group',
        title={
            'text': f"<b>{title}</b>",
            'y':0.97,
            'x':0.5,
            'xanchor': 'center',
            'yanchor': 'top'
        },
        xaxis_title="Metric Type",
        yaxis_title="Memory (KB)",
        legend=dict(
            orientation="h",
            yanchor="bottom",
            y=1.07,
            xanchor="center",
            x=0.5,
            font=dict(size=9),
            itemclick='toggleothers'
        ),
        font=dict(size=9)
    )
    fig.show()

plot_metric(
    df,
    value_col='HeapSize',
    title='Heap Size Comparison (XML vs Compose)'
)

plot_metric(
    df,
    value_col='RssAnon',
    title='RSS Anon Comparison (XML vs Compose)'
)
