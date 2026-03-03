package org.blackstamp.sleepychronicles.api.data.days;

import lombok.Getter;
import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.api.constant.ConstantFields;
import org.blackstamp.sleepychronicles.api.data.json.JsonManager;
import org.blackstamp.sleepychronicles.game.listener.day.DayChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class DayManager {

    private final JsonManager<DayData> jsonManager;
    private final DayData dayData;
    @Getter private static DayManager instance;

    private BukkitTask timeTask;

    public DayManager(){
        jsonManager = new JsonManager<>(
                SleepyChronicles.getInstance(),
                DayData.class.getSimpleName(),
                DayData.class
        );
        dayData = jsonManager.getOrCreate();
        instance = this;

        if(isDayOver()){
            final long difference = System.currentTimeMillis() - dayData.getTimestamp();
            final int skippedDays = Math.toIntExact(difference / ConstantFields.ONE_DAY) + 1;

            if(isMaxDay()) return;

            setDay(dayData.getDay() + skippedDays);
            dayData.setTimestamp(dayData.getTimestamp() + (ConstantFields.ONE_DAY * skippedDays));
        }
        final long remaining = dayData.getTimestamp() - System.currentTimeMillis();

        scheduleDayChange(remaining);
    }

    public void setDay(@NotNull Integer value){
        dayData.setDay(value);
        saveData();
    }

    public int getDay(){ return dayData.getDay(); }

    public void setTimestamp(@NotNull Long value){
        dayData.setTimestamp(value);
        saveData();
    }

    public long getTimestamp(){ return dayData.getTimestamp(); }

    public String convertToTime(long value){
        final long remaining = value - System.currentTimeMillis();

        final long hours = remaining / ConstantFields.ONE_HOUR;
        final long mins = (remaining - (hours * ConstantFields.ONE_HOUR)) / ConstantFields.ONE_MINUTE;
        final long seconds = (remaining - (hours * ConstantFields.ONE_HOUR) - (mins * ConstantFields.ONE_MINUTE)) / ConstantFields.ONE_SECOND;

        return hours + "h, " + mins + "m" + " and " + seconds + "s";
    }

    private boolean isDayOver(){
        return System.currentTimeMillis() >= dayData.getTimestamp();
    }

    private boolean isMaxDay(){ return dayData.getDay() >= dayData.getMaxDay(); }

    private void dayChange(){
        final long newTimestamp = dayData.getTimestamp() + ConstantFields.ONE_DAY;

        dayData.setDay(dayData.getDay() + 1);
        dayData.setTimestamp(newTimestamp);

        saveData();

        if(timeTask != null) timeTask.cancel();

        new DayChangeEvent(dayData.getDay()).callEvent();

        if(isMaxDay()) return;

        scheduleDayChange(ConstantFields.ONE_DAY);
    }

    private void scheduleDayChange(long delay){
        timeTask = Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), this::dayChange, (delay / 1000) * 20);
    }

    private void saveData(){
        Bukkit.getScheduler().runTaskAsynchronously(SleepyChronicles.getInstance(), () -> jsonManager.save(dayData));
    }
}