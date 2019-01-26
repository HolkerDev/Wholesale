package com.dev.holker.wholesale.model;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.dev.holker.wholesale.R;
import com.parse.*;

public class RateDialog extends DialogFragment {

    public Boolean client;
    public String orderId;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rate, null);
        builder.setView(view);

        ImageView minus = view.findViewById(R.id.rate_minus);
        ImageView plus = view.findViewById(R.id.rate_plus);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseQuery queryOrder = new ParseQuery<ParseObject>("Order");
                queryOrder.whereEqualTo("objectId", orderId);

                try {
                    final ParseObject order = queryOrder.getFirst();

                    if (client) {
                        order.put("ratedClient", true);
                        Log.i("MyLog", "Client here");
                        order.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseQuery queryOffer = new ParseQuery<ParseObject>("OrderOffer");
                                queryOffer.whereEqualTo("order", order);
                                Log.i("MyLog", "Client here 1: " + order.getObjectId());
                                queryOffer.whereEqualTo("status", "Accepted");
                                ParseObject offer = new ParseObject("OrderOffer");
                                try {
                                    offer = queryOffer.getFirst();
                                    ParseUser supplier = offer.getParseUser("user");
                                    ParseQuery queryRating = new ParseQuery<ParseObject>("Rating");
                                    queryRating.whereEqualTo("user", supplier);
                                    final ParseObject rating = queryRating.getFirst();
                                    rating.increment("rate", -1);
                                    rating.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Log.i("MyLog", "Client here 3 :" + rating.getObjectId());
                                            RateDialog.this.getDialog().cancel();
                                        }
                                    });
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                    } else {
                        order.put("ratedSupplier", true);
                        order.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseUser userClient = order.getParseUser("user");
                                ParseQuery queryRating = new ParseQuery<ParseObject>("Rating");
                                queryRating.whereEqualTo("user", userClient);
                                try {
                                    ParseObject rating = queryRating.getFirst();
                                    rating.increment("rate", -1);
                                    rating.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            RateDialog.this.getDialog().cancel();
                                        }
                                    });
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseQuery queryOrder = new ParseQuery<ParseObject>("Order");
                queryOrder.whereEqualTo("objectId", orderId);

                try {
                    final ParseObject order = queryOrder.getFirst();

                    if (client) {
                        order.put("ratedClient", true);
                        Log.i("MyLog", "Client here");
                        order.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseQuery queryOffer = new ParseQuery<ParseObject>("OrderOffer");
                                queryOffer.whereEqualTo("order", order);
                                Log.i("MyLog", "Client here 1: " + order.getObjectId());
                                queryOffer.whereEqualTo("status", "Accepted");
                                ParseObject offer = new ParseObject("OrderOffer");
                                try {
                                    offer = queryOffer.getFirst();
                                    ParseUser supplier = offer.getParseUser("user");
                                    ParseQuery queryRating = new ParseQuery<ParseObject>("Rating");
                                    queryRating.whereEqualTo("user", supplier);
                                    final ParseObject rating = queryRating.getFirst();
                                    rating.increment("rate", 1);
                                    rating.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Log.i("MyLog", "Client here 3 :" + rating.getObjectId());
                                            RateDialog.this.getDialog().cancel();
                                        }
                                    });
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                    } else {
                        order.put("ratedSupplier", true);
                        order.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseUser userClient = order.getParseUser("user");
                                ParseQuery queryRating = new ParseQuery<ParseObject>("Rating");
                                queryRating.whereEqualTo("user", userClient);
                                try {
                                    ParseObject rating = queryRating.getFirst();
                                    rating.increment("rate", 1);
                                    rating.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            RateDialog.this.getDialog().cancel();
                                        }
                                    });
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return builder.create();
    }
}
