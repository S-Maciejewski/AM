using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Runtime;
using Android.Widget;

namespace AM
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        TextView textDebug;
        Button connectButton, startButton, stopButton;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);
            textDebug = FindViewById<TextView>(Resource.Id.textDebug);
            connectButton = FindViewById<Button>(Resource.Id.connectButton);
            startButton = FindViewById<Button>(Resource.Id.startButton);
            stopButton = FindViewById<Button>(Resource.Id.stopButton);
            textDebug.Text = "App debug:\n";

            connectButton.Click += delegate {
                textDebug.Text = "Connecting...";
            };

            startButton.Click += delegate {
                textDebug.Text = "Starting";
            };

            stopButton.Click += delegate {
                textDebug.Text = "Stopping";
            };
        }
    }
}