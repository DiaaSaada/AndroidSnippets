public final static String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

private BroadcastReceiver receiver;



    private void connectionReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(CONNECTIVITY_CHANGE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                        Log.e("Network", "Internet YAY");
                        findViewById(R.id.layout_no_connection).setVisibility(View.GONE);
                    } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                        Log.e("Network", "No internet :(");
                        findViewById(R.id.layout_no_connection).setVisibility(View.VISIBLE);
                    }

                }
            }
        };
        registerReceiver(receiver, filter);

    }