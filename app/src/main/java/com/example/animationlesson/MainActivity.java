package com.example.animationlesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.animationlesson.auth.AuthFragment;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottie;
    ImageView imageView;
    Button btnJump;
    Button btnSimple;
    Button btnFlip;
    Button btnIcon;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottie = findViewById(R.id.lottie_animation_view);
        btnJump = findViewById(R.id.jump);
        btnSimple = findViewById(R.id.simple_btn);
        btnFlip = findViewById(R.id.flip_btn);
        btnIcon = findViewById(R.id.icon_btn);
        imageView = findViewById(R.id.image_view);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, AuthFragment.class,null)
           .commit();
        }


        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setAnimation("jump.json");
                lottie.playAnimation();
            }
        });

        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setAnimation("flip.json");
                lottie.playAnimation();
            }
        });
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setAnimation("simple.json");
                lottie.playAnimation();
            }
        });
        btnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setAnimation("icon.json");
                lottie.playAnimation();
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                @SuppressLint("ObjectAnimatorBinding") final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scalex", 1f, 0f);
                @SuppressLint("ObjectAnimatorBinding") final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scalex", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());

                Animation flip = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                flip.setInterpolator(new LinearInterpolator());
                flip.setRepeatCount(Animation.INFINITE);
                flip.setDuration(400);

                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        imageView.startAnimation(flip);
                        imageView.setImageResource(R.drawable.bottle);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        flip.cancel();
                        imageView.setImageResource(R.drawable.image_cat);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

        findViewById(R.id.bounce_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.setAnimation("bounce.json");
            }
        });
        findViewById(R.id.jump_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = null;
                doBounceAnimation(view);
            }
        });

    }

    private void doBounceAnimation(View targetView) {
        android.view.animation.Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return getPowOut(input, 2);
            }
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setRepeatCount(5);
        animator.start();

    }

    private float getPowOut(float elapsedTimeRate, double pow) {
        return (float) ((float) 1 - Math.pow(1 - elapsedTimeRate, pow));
    }


}