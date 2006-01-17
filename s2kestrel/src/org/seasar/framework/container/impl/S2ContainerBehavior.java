package org.seasar.framework.container.impl;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;

/**
 * <p>
 * {@link S2ContainerImpl}の振る舞いを変更できるようにするためのエクステンションポイントです。内部インタフェース {@link Provider}
 * を実装した独自のクラスを登録することで、 {@link S2ContainerImpl}の振る舞いを
 * {@link org.seasar.framework.container.S2Container}に準拠しないように変更することが出来ます。
 * </p>
 * <p>
 * {@link S2ContainerImpl}の振る舞いを変更することにより、 {@link org.seasar.framework.container.S2Container}
 * の仕様に依存したコンポーネントやS2関連プロダクトが正常に動作しなくなることもあり得ます。 <strong>Own Risk </strong>で使用してください。
 * <p>
 * このクラスは {@link S2ContainerImpl}の実装に強く依存しています。将来に渡って安定したものでは <strong>ない
 * </strong>ことを理解した上で使用してください。
 * </p>
 * 
 * @author koichik
 */
public final class S2ContainerBehavior {
    private static Provider provider_ = new DefaultProvider();

    private S2ContainerBehavior() {
    }

    /**
     * 登録されている {@link Provider}のインスタンスを返します。
     * 
     * @return 登録されている {@link Provider}のインスタンス
     */
    public static Provider getProvider() {
        return provider_;
    }

    /**
     * {@link Provider}のインスタンスを登録します。
     * 
     * @param provider
     *            登録する {@link Provider}のインスタンス
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
     * S2コンテナの振る舞いを提供するためのインタフェースです。
     * 
     * @author koichik
     */
    public interface Provider {
        /**
         * {@link S2ContainerImpl#getComponent()}がコンポーネント定義を取得する際に呼び出されます。 <br>
         * {@link org.seasar.framework.container.S2Container#getComponent()}
         * の仕様では、コンポーネント定義が見つからない場合は
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * がスローされることになっています。
         * 
         * @param container
         *            呼び出し元のコンテナです。
         * @param key
         *            取得したいコンポーネント定義のキーです。
         * @return コンポーネント定義です。コンポーネント定義が見つからず、例外をスローしない場合には <code>null</code> を返すことが出来ます。
         */
        ComponentDef acquireFromGetComponent(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#getComponentDef()}がコンポーネント定義を取得する際に呼び出されます。 <br>
         * {@link org.seasar.framework.container.S2Container#getComponentDef()}
         * の仕様では、コンポーネント定義が見つからない場合は
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * がスローされることになっています。
         * 
         * @param container
         *            呼び出し元のコンテナです。
         * @param key
         *            取得したいコンポーネント定義のキーです。
         * @return コンポーネント定義です。コンポーネント定義が見つからず、例外をスローしない場合には <code>null</code> を返すことが出来ます。
         */
        ComponentDef acquireFromGetComponentDef(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#hasComponent()}がコンポーネント定義を取得する際に呼び出されます。 <br>
         * {@link org.seasar.framework.container.S2Container#hasComponent()}
         * の仕様では、コンポーネント定義が見つからなくても例外はスローされないことになっています。
         * 
         * @param container
         *            呼び出し元のコンテナです。
         * @param key
         *            取得したいコンポーネント定義のキーです。
         * @return コンポーネント定義です。コンポーネント定義が見つからず、例外をスローしない場合には <code>null</code> を返すことが出来ます。
         */
        ComponentDef acquireFromHasComponentDef(S2Container container, final Object key);

        /**
         * {@link S2ContainerImpl#injectDependency()}がコンポーネント定義を取得する際に呼び出されます。 <br>
         * {@link org.seasar.framework.container.S2Container#injectDependency()}
         * の仕様では、コンポーネント定義が見つからない場合は
         * {@link org.seasar.framework.container.ComponentNotFoundRuntimeException}
         * がスローされることになっています。
         * 
         * @param container
         *            呼び出し元のコンテナです。
         * @param key
         *            取得したいコンポーネント定義のキーです。
         * @return コンポーネント定義です。コンポーネント定義が見つからず、例外をスローしない場合には <code>null</code> を返すことが出来ます。
         */
        ComponentDef acquireFromInjectDependency(S2Container container, final Object key);
    }

    /**
     * {@link org.seasar.framework.container.S2Container}の仕様に従った振る舞いを提供する、 {@link Provider}の実装です。
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
