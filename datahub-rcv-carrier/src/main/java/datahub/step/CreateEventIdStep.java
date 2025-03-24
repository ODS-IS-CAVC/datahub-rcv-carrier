package datahub.step;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * イベントID作成
 */
@Component
public class CreateEventIdStep extends BaseStep {
	public CreateEventIdStep(MessageSource ms) {
		super(ms);
	}

	/**
	 * イベントIDを生成する。
	 * 形式 : yyyyMMddHHmmssSSS
	 */
	public String execute() {
		String eventId = this.createEventId();
		logger.info("イベントIDを生成しました:" + eventId);

		return eventId;
	}

	/**
	 * イベントIDを生成する
	 */
	public String createEventId() {
		return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
	}
}
