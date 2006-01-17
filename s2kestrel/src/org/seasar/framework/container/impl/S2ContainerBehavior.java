package org.seasar.framework.container.impl;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;

/**
 * <p>
 * {@link S2ContainerImpl}�̐U�镑����ύX�ł���悤�ɂ��邽�߂̃G�N�X�e���V�����|�C���g�ł��B�����C���^�t�F�[�X {@link Provider}
 * �����������Ǝ��̃N���X��o�^���邱�ƂŁA {@link S2ContainerImpl}�̐U�镑����
 * {@link org.seasar.framework.container.S2Container}�ɏ������Ȃ��悤�ɕύX���邱�Ƃ��o���܂��B
 * </p>
 * <p>
 * {@link S2ContainerImpl}�̐U�镑����ύX���邱�Ƃɂ��A {@link org.seasar.framework.container.S2Container}
 * �̎d�l�Ɉˑ������R���|�[�l���g��S2�֘A�v���_�N�g������ɓ��삵�Ȃ��Ȃ邱�Ƃ����蓾�܂��B <strong>Own Risk </strong>�Ŏg�p���Ă��������B
 * <p>
 * ���̃N���X�� {@link S2ContainerImpl}�̎����ɋ����ˑ����Ă��܂��B�����ɓn���Ĉ��肵�����̂ł� <strong>�Ȃ�
 * </strong>���Ƃ𗝉�������Ŏg�p���Ă��������B
 * </p>
 * 
 * @author koichik
 */
public final class S2ContainerBehavior {
    private static Provider provider_ = new DefaultProvider();

    private S2ContainerBehavior() {
    }

    /**
     * �o�^����Ă��� {@link Provider}�̃C���X�^���X��Ԃ��܂��B
     * 
     * @return �o�^����Ă��� {@link Provider}�̃C���X�^���X
     */
    public static Provider getProvider() {
        return provider_;
    }

    /**
     * {@link Provider}�̃C���X�^���X��o�^���܂��B
     * 
     * @param provider
     *            �o�^���� {@link Provider}�̃C���X�^���X
     */
    public static void setProvider(final Provider provider) {
        provider_ = provider;
    }

    public static ComponentDef acquireFromGetComponent(S2Container container, final Object key) {
        return getProvider().acquireFromGetComponent(container, key);
    }

    public static ComponentDef acquireFromGetComponentDef(S2Container container, final Object key) {
        return getProvider().acquireFromGetComponentDef(container, key);
    }

    public static ComponentDef acquireFromHasComponentDef(S2Container container, final Object key) {
        return getProvider().acquireFromHasComponentDef(container, key);
    }

    public static ComponentDef acquireFromInjectDependency(S2Container container, final Object key) {
        return getProvider().acquireFromInjectDependency(container, key);
    }

    /**
     * S2�R���e�i�̐U�镑����񋟂��邽�߂̃C���^�t�F�[�X�ł��B
     * 
     * @author koichik
     */
    public interface Provider {
        /**
         * {@link S2ContainerImpl#getComponent()}���R���|�[�l���g��`���擾����ۂɌĂяo����܂��B <br>
         * {@link org.seasar.framework.container.S2Container#getComponent()}
         * �̎d�l�ł́A�R���|�[�l���g��`��������Ȃ��ꍇ��
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * ���X���[����邱�ƂɂȂ��Ă��܂��B
         * 
         * @param container
         *            �Ăяo�����̃R���e�i�ł��B
         * @param key
         *            �擾�������R���|�[�l���g��`�̃L�[�ł��B
         * @return �R���|�[�l���g��`�ł��B�R���|�[�l���g��`�������炸�A��O���X���[���Ȃ��ꍇ�ɂ� <code>null</code> ��Ԃ����Ƃ��o���܂��B
         */
        ComponentDef acquireFromGetComponent(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#getComponentDef()}���R���|�[�l���g��`���擾����ۂɌĂяo����܂��B <br>
         * {@link org.seasar.framework.container.S2Container#getComponentDef()}
         * �̎d�l�ł́A�R���|�[�l���g��`��������Ȃ��ꍇ��
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * ���X���[����邱�ƂɂȂ��Ă��܂��B
         * 
         * @param container
         *            �Ăяo�����̃R���e�i�ł��B
         * @param key
         *            �擾�������R���|�[�l���g��`�̃L�[�ł��B
         * @return �R���|�[�l���g��`�ł��B�R���|�[�l���g��`�������炸�A��O���X���[���Ȃ��ꍇ�ɂ� <code>null</code> ��Ԃ����Ƃ��o���܂��B
         */
        ComponentDef acquireFromGetComponentDef(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#hasComponent()}���R���|�[�l���g��`���擾����ۂɌĂяo����܂��B <br>
         * {@link org.seasar.framework.container.S2Container#hasComponent()}
         * �̎d�l�ł́A�R���|�[�l���g��`��������Ȃ��Ă���O�̓X���[����Ȃ����ƂɂȂ��Ă��܂��B
         * 
         * @param container
         *            �Ăяo�����̃R���e�i�ł��B
         * @param key
         *            �擾�������R���|�[�l���g��`�̃L�[�ł��B
         * @return �R���|�[�l���g��`�ł��B�R���|�[�l���g��`�������炸�A��O���X���[���Ȃ��ꍇ�ɂ� <code>null</code> ��Ԃ����Ƃ��o���܂��B
         */
        ComponentDef acquireFromHasComponentDef(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#injectDependency()}���R���|�[�l���g��`���擾����ۂɌĂяo����܂��B <br>
         * {@link org.seasar.framework.container.S2Container#injectDependency()}
         * �̎d�l�ł́A�R���|�[�l���g��`��������Ȃ��ꍇ��
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * ���X���[����邱�ƂɂȂ��Ă��܂��B
         * 
         * @param container
         *            �Ăяo�����̃R���e�i�ł��B
         * @param key
         *            �擾�������R���|�[�l���g��`�̃L�[�ł��B
         * @return �R���|�[�l���g��`�ł��B�R���|�[�l���g��`�������炸�A��O���X���[���Ȃ��ꍇ�ɂ� <code>null</code> ��Ԃ����Ƃ��o���܂��B
         */
        ComponentDef acquireFromInjectDependency(S2Container container, final Object key);
    }

    /**
     * {@link org.seasar.framework.container.S2Container}�̎d�l�ɏ]�����U�镑����񋟂���A {@link Provider}�̎����ł��B
     * 
     * @author koichik
     */
    public static class DefaultProvider implements Provider {
        public ComponentDef acquireFromGetComponent(final S2Container container, final Object key) {
            return acquireFromGetComponentDef(container, key);
        }

        public ComponentDef acquireFromGetComponentDef(final S2Container container, final Object key) {
            final ComponentDef cd = getComponentDef(container, key);
            if (cd == null) {
                throw new ComponentNotFoundRuntimeException(key);
            }
            return cd;
        }

        public ComponentDef acquireFromHasComponentDef(final S2Container container, final Object key) {
            return getComponentDef(container, key);
        }

        public ComponentDef acquireFromInjectDependency(final S2Container container,
                final Object key) {
            return acquireFromGetComponentDef(container, key);
        }

        protected ComponentDef getComponentDef(final S2Container container, final Object key) {
            return ((S2ContainerImpl) container).internalGetComponentDef(key);
        }
    }
}
